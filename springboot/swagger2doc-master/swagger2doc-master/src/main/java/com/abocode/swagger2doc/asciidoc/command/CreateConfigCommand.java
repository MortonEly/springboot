package com.abocode.swagger2doc.asciidoc.command;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * Created on 2018/12/29.
 *
 * @author guanxf
 */
@ApiModel(value = "配置信息")
public class CreateConfigCommand implements Serializable {
    @ApiModelProperty(value = "名称,2-20",notes = "名称，长度2~20",required = true)
    @NotNull
    @Size(min = 2,max = 20)
    private String name;
    @ApiModelProperty(value = "代码",notes = "代码",required = true)
    @NotNull
    @Size(min = 2,max = 30)
    private String code;
    @ApiModelProperty(value = "值",notes = "值",required = true)
    @NotNull
    @Size(min = 1,max = 2000)
    private String value;
    @ApiModelProperty(value = "描述",notes = "描述",required = true)
    @NotNull
    @Size(min = 2,max = 50)
    private String description;
    @ApiModelProperty(value = "单位",notes = "单位")
    @Size(max = 20)
    private String unit;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
