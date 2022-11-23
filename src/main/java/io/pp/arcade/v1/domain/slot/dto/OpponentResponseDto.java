package io.pp.arcade.v1.domain.slot.dto;

import io.pp.arcade.v1.domain.slot.Opponent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OpponentResponseDto {

    private String intraId;
    private String nick;
    private String imageUrl;
    private String detail;
    @Builder
    public OpponentResponseDto(Opponent opponent) {
        this.intraId = opponent.getIntraId();
        this.nick = opponent.getNick();
        this.imageUrl = opponent.getImageUrl();
        this.detail = opponent.getDetail();
    }

    public static OpponentResponseDto from(Opponent opponent) {
        return OpponentResponseDto.builder().opponent(opponent).build();
    }
}
