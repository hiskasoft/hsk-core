package com.hiska.jaxrs;

import java.io.Serializable;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HashLong implements Serializable {

    public Long value;

}
