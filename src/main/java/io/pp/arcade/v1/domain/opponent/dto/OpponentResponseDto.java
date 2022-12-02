package io.pp.arcade.v1.domain.opponent.dto;

import io.pp.arcade.v1.domain.opponent.Opponent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OpponentResponseDto {

    private String intraId;
    private String nick;
    private String imageUrl;
    private String detail1;
    private String detail2;
    private String detail3;
    private Boolean isReady;

    @Builder
    public OpponentResponseDto(Opponent opponent) {
        this.intraId = opponent.getIntraId();
        this.nick = opponent.getNick();
        this.imageUrl = opponent.getImageUrl();
        this.detail1 = opponent.getDetail1();
        this.detail2 = opponent.getDetail2();
        this.detail3 = opponent.getDetail3();
        this.isReady = opponent.getIsReady();
    }

    @Override
    public String toString() {
        return "OpponentResponseDto{" +
                "intraId='" + intraId + '\'' +
                ", nick='" + nick + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", detail1='" + detail1 + '\'' +
                ", detail2='" + detail2 + '\'' +
                ", detail3='" + detail3 + '\'' +
                ", isReady=" + isReady +
                '}';
    }

    public static OpponentResponseDto from(Opponent opponent) {
        return new OpponentResponseDto(opponent);
    }
}
