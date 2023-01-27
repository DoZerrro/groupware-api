package com.dq.aquaranth.menu.objectstorage.service;

import com.dq.aquaranth.menu.objectstorage.dto.request.ObjectGetRequestDTO;
import com.dq.aquaranth.menu.objectstorage.dto.response.ObjectResponseDTO;
import com.dq.aquaranth.menu.objectstorage.dto.request.ObjectPostRequestDTO;

public interface ObjectStorageService {

    ObjectResponseDTO postObject(ObjectPostRequestDTO objectPostRequestDTO) throws Exception;

    ObjectResponseDTO getObject(ObjectGetRequestDTO objectGetRequestDTO) throws Exception;

}
