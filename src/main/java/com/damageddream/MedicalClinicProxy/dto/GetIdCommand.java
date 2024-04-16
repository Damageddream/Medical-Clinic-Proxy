package com.damageddream.MedicalClinicProxy.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GetIdCommand {
    private Long entityId;
}