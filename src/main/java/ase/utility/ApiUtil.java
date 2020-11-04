package ase.utility;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ApiUtil {

    public String encodeUriForGet(MultiValueMap<String, String> params, String uri) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        return builder.queryParams(params).build().encode().toString();
    }
}
