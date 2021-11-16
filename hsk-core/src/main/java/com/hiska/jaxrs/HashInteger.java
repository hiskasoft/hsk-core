package com.hiska.jaxrs;

import java.io.Serializable;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HashInteger implements Serializable {

    public Integer value;

}
