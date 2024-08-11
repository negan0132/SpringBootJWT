package com.SpringBoot.dto.request;

import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassRequest {
	@Size(min = 3, max = 20, message = "Ten lop only has 3 to 20 charecters")
	 String tenLop;
	 List<Long> listStudent;
     long khoa;
}
