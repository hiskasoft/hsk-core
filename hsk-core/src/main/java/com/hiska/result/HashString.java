package com.hiska.result;

import java.io.Serializable;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HashString implements Serializable {

    public String value;

}
