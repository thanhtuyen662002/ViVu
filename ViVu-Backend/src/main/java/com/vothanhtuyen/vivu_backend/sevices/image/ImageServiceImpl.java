package com.vothanhtuyen.vivu_backend.sevices.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ImageServiceImpl implements ImageService{
    @Value("${cx.key}")
    private String CX_KEY;

    @Value("${cx.key2}")
    private String CX_KEY2;

    @Value("${image.key}")
    private String IMAGE_KEY;

    @Value("${image.key2}")
    private String IMAGE_KEY2;

    private String getImageUrl(String name) {
        return "https://www.googleapis.com/customsearch/v1?key=" + IMAGE_KEY + "&cx=" + CX_KEY + "&searchType=image&q=" + name;
    }

    private String getImageUrl2(String name) {
        return "https://www.googleapis.com/customsearch/v1?key=" + IMAGE_KEY2 + "&cx=" + CX_KEY2 + "&searchType=image&q=" + name;
    }

    @Override
    public String getImage(String name) {
        try {
            // Tạo URL cho API request
            String apiUrl = getImageUrl(name);
            String apiUrl2 = getImageUrl2(name);

            // Sử dụng RestTemplate để gửi yêu cầu GET
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(apiUrl, String.class);
            if (response == null) {
                response = restTemplate.getForObject(apiUrl2, String.class);
            }

            // Parse JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            // Lấy link của hình ảnh đầu tiên trong kết quả
            JsonNode itemsNode = rootNode.path("items");
            if (itemsNode.isArray() && itemsNode.size() > 0) {
                // Lấy link của hình ảnh từ trường "link"
                String imageUrl = itemsNode.get(0).path("link").asText();
                return imageUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
