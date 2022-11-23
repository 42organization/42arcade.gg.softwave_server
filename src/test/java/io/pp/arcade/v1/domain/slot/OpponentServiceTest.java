package io.pp.arcade.v1.domain.slot;

import io.pp.arcade.v1.domain.slot.dto.OpponentResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class OpponentServiceTest {

    @Autowired
    private OpponentRepository opponentRepository;

    @Autowired
    private OpponentService opponentService;

    @Test
    void 유저가_조회된다() {
        //given
        Opponent opponent1 = new Opponent("salee2", "sal", "http:google.com", "hihi");
        //when
        OpponentResponseDto responseDto = opponentService.findOne(opponent1.getId());

        //then
        assertThat(responseDto.getIntraId()).isEqualTo(opponent1.getIntraId());
        assertThat(responseDto.getNick()).isEqualTo(opponent1.getNick());
        assertThat(responseDto.getDetail()).isEqualTo(opponent1.getDetail());
    }

}