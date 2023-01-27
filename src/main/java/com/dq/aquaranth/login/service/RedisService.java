package com.dq.aquaranth.login.service;

import com.dq.aquaranth.login.constant.RedisKeys;
import com.dq.aquaranth.login.dto.LoginUserInfo;
import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.dto.response.MenuResponseDTO;
import com.dq.aquaranth.menu.service.MenuConfigurationService;
import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.dq.aquaranth.rolegroup.service.RoleGroupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 레디스 db 접근에 대한 비즈니스 로직을 처리합니다.
 *
 * @author 임종현
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final MenuConfigurationService menuConfigurationService;
    private final RoleGroupService roleGroupService;

    /**
     * redis 에 데이터 저장을 요청합니다.
     * object mapper 를 통하여 입력받은 객체를 직렬화해서 value 로 저장합니다.
     *
     * @param key - redis 에 저장할 key
     * @param value - 전달받은 key 에 대한 value
     */
    public void setCacheObject(final String key, final Object value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String parseValue = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(value);
        redisTemplate.opsForValue().set(key, parseValue);
    }

    /**
     * redis 에 데이터 저장을 요청합니다.
     * 일정시간이 지나면 데이터 자동소멸 될 수 있도록 만료시간을 설정할 수 있습니다.
     * @param key - redis 에 저장할 key
     * @param value - 전달받은 key 에 대한 value
     * @param timeout - 만료시간
     * @param timeUnit - 만료시간 단위
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 저장된 데이터의 만료시간 재설정을 요청합니다.
     *
     * @param key - redis 에 저장되어 있는 key
     * @param timeout - 재설정할 만료시간
     * @return true=성공시    ；false=실패시
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 저장된 데이터의 만료시간 재설정을 요청합니다.
     * 시간단위를 설정할 수 있습니다.
     *
     * @param key - redis 에 저장되어 있는 key
     * @param timeout - 재설정할 만료시간
     * @param unit - 만료시간의 시간단위
     * @return true=성공시    ；false=실패시
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * TODO: 반환객체를 역직렬화 해서 주어야 하나요?
     * redis 의 value 를 요청합니다.
     *
     * @param key - value 를 검색할 key
     * @return value
     */
    public <T> Object getCacheObject(final String key) {
        ValueOperations<String, Object> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 저장된 데이터를 삭제합니다.
     *
     * @param key - 삭제할 데이터의 key
     */
    public boolean deleteObject(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 저장된 데이터를 삭제합니다.
     * 여라개의 key 를 입력받을 수 있습니다.
     *
     * @param collection - 삭제할 key 들
     * @return 삭제된 데이터 갯수
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     *  데이터를 저장합니다
     *  단, 자료형은 List 입니다.
     *
     * @param key - 저장할 데이터의 key
     * @param dataList - 데이터 list
     * @return - 저장된 데이터 갯수
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 저장된 데이터를 요청합니다.
     * 단, 자료형은 List 입니다.
     *
     * @param key - 저장된 데이터를 찾을 key
     * @return - 찾은 데이터 리스트
     */
    public <T> List<Object> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 데이터를 저장합니다.
     * 단, 자료형은 Set 입니다.
     *
     * @param key - 저장된 데이터를 찾을 key
     * @param dataSet - 저장할 set
     * @return - ?
     */
    public <T> BoundSetOperations<String, Object> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, Object> setOperation = redisTemplate.boundSetOps(key);
        for (T t : dataSet) {
            setOperation.add(t);
        }
        return setOperation;
    }

    /**
     * 저장된 데이터를 요청합니다.
     * 단, 자료형은 Set 입니다.
     *
     * @param key - 저장된 데이터를 찾을 key
     * @return - 찾은 Set
     */
    public <T> Set<Object> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     *   Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * Map
     *
     * @param key
     * @return
     */
    public <T> Map<Object, Object> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     *  Hash
     *
     * @param key   Redis
     * @param hKey  Hash
     * @param value
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     *   Hash
     *
     * @param key  Redis
     * @param hKey Hash
     * @return Hash
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * Hash
     *
     * @param key   Redis
     * @param hKeys Hash
     * @return Hash
     */
    public <T> List<Object> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 저장된 key 를 검색합니다.
     *
     * @param pattern - key 를 찾을 조건식
     * @return - 찾은 key 값들
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void updateMenuRoles() {
        // db 에 저장된 모든 메뉴를 가져옵니다.
        List<String> menuCodes = new ArrayList<>();
        for (MenuResponseDTO param : menuConfigurationService.findAllBy(new MenuQueryDTO())) {
            menuCodes.add(param.getMenuCode());
        }

        // 메뉴코드에 매핑된 권한그룹들을 전부 가져와서, menuCode, roleGroupNo 형태로 저장합니다.
        menuCodes.forEach(menuCode -> {
            List<RoleGroup> roleGroups = new ArrayList<>(roleGroupService.findByMenuCode(menuCode));
            try {
                setCacheObject(RedisKeys.ROLE_KEY.getKey() + menuCode, roleGroups);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<Object> findAllUsers() {
        return getCacheList("U_");
    }
}
