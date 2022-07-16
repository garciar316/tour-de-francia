package sofka.reto.tour_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDTO {

    private String id;
    @NotBlank
    @Size(max=3)
    private String code;
    @NotBlank
    private String fullName;
    @NotBlank
    private String nationality;
    @NotNull
    private String teamCode;
}
