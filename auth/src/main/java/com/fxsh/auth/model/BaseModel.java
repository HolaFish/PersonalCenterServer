package com.fxsh.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BaseModel {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableLogic(value = "0",delval = "1")
    private boolean isDeleted;
    private Integer createdBy;
    private LocalDateTime createdAt;
    private Integer updatedBy;
    private LocalDateTime updatedAt;
}
