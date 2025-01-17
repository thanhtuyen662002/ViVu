package com.vothanhtuyen.vivu_backend.sevices.data;

import com.vothanhtuyen.vivu_backend.dto.DataResponseDTO;
import com.vothanhtuyen.vivu_backend.dto.GetDataRequestDTO;

public interface DataService {
    DataResponseDTO getData(GetDataRequestDTO request);
}
