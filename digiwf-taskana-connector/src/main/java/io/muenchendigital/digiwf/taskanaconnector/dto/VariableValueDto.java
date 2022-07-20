package io.muenchendigital.digiwf.taskanaconnector.dto;

import java.util.Map;

/** This class is represents a DTO which contains a process variable and its info. */
public class VariableValueDto {

  protected String type;

  protected Object value;

  protected Map<String, Object> valueInfo;

  public VariableValueDto() {}

  public VariableValueDto(String type, Object value, Map<String, Object> valueInfo) {
    this.type = type;
    this.value = value;
    this.valueInfo = valueInfo;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public Map<String, Object> getValueInfo() {
    return valueInfo;
  }

  public void setValueInfo(Map<String, Object> valueInfo) {
    this.valueInfo = valueInfo;
  }
}
