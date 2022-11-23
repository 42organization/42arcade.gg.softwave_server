package io.pp.arcade.v1.domain.slot;

import io.pp.arcade.v1.domain.slot.dto.OpponentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class OpponentService {

    private final OpponentRepository opponentRepository;

    @Transactional(readOnly = true)
    public OpponentResponseDto findOne(Integer opponentId) {
        Opponent opponent = opponentRepository.findById(opponentId).orElseThrow();
        return OpponentResponseDto.from(opponent);
    }
}
