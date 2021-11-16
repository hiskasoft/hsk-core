package com.hiska.jaxrs;

import java.io.Serializable;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HashString implements Serializable {

    public String value;

}
