package com.dq.aquaranth.menu.interceptor;

import com.dq.aquaranth.login.constant.RedisKeys;
import com.dq.aquaranth.login.service.UserSessionService;
import com.dq.aquaranth.menu.annotation.MenuCode;
import com.dq.aquaranth.menu.constant.MenuCodes;
import com.dq.aquaranth.menu.exception.CommonException;
import com.dq.aquaranth.menu.exception.ErrorCodes;
import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * 권한 체크용 인터셉터입니다.
 * 컨트롤러로 들어오는 유저 정보와, 접근하려는 컨트롤러를 확인하여
 * 유저의 권한그룹들을 조회한 뒤, 컨트롤러에 접근할 수 있는 권한그룹과
 * 일치한지 확인합니다.
 * <p>
 * 컨트롤러에는 커스텀 어노테이션으로 메뉴코드가 부여되어 있고,
 * 컨트롤러로 접근하는 어노테이션 메뉴코드 값을 읽고,
 * 레디스에 해당하는 메뉴코드 키값을 검색하여
 * 권한 그룹을 조회합니다.
 * <p>
 * 해당하는 권한그룹이 있다면 요청을 허가하고,
 * 해당하는 권한그룹이 없다면 예외와 함께 요청을 차단합니다.
 *
 * @author 김민준
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final UserSessionService userSessionService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String username = null;
        String menuCode = null;
        try{
            username = request.getUserPrincipal().getName();
            menuCode = handlerMethod.getBean().getClass().getDeclaredAnnotation(MenuCode.class).value().getCode();

        if (menuCode.equals(MenuCodes.ROOT.getCode())) {
            return true;
        }
        List<RoleGroup> loginUserInfo = userSessionService.findUserInfoInRedis(username).getRoleGroups();
        List<RoleGroup> menuRoles = objectMapper.readValue(redisTemplate.opsForValue().get(RedisKeys.ROLE_KEY.getKey() + menuCode).toString(), new TypeReference<>() {});
        loginUserInfo.stream()
                .filter(loginUser -> menuRoles.stream().anyMatch(menuRole -> loginUser.getRoleGroupNo().equals(menuRole.getRoleGroupNo())))
                .findAny().orElseThrow(NullPointerException::new);}
        catch (NullPointerException e){
            request.getRequestDispatcher("/api/menu/error").forward(request,response);
            log.info("{} 에 대한 {} 메뉴권한은 허가되지 않았습니다.", username, menuCode);
            return false;
        }
        log.info("{} 에 대한 {} 메뉴권한 확인", username, menuCode);

        return true;
    }
}
