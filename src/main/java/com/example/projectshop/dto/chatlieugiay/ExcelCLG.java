package com.example.projectshop.dto.chatlieugiay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExcelCLG {
    private Integer stt;
    private String tenChatLieuGiay;
    private String trangThai;
}
