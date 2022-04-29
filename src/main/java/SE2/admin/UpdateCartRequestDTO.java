package SE2.admin;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCartRequestDTO {

    private List<CartProductDTO> cartProductDTOList;
}
