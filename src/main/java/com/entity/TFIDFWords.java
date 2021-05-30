package com.entity;

import lombok.Data;

import java.util.List;

@Data
public class TFIDFWords {
    List<String> high;
    List<String> mid;
    List<String> low;

}
