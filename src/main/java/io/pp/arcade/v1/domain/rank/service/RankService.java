package io.pp.arcade.v1.domain.rank.service;

import io.jsonwebtoken.lang.Collections;
import io.pp.arcade.v1.domain.admin.dto.create.RankCreateRequestDto;
import io.pp.arcade.v1.domain.admin.dto.delete.RankDeleteDto;
import io.pp.arcade.v1.domain.admin.dto.update.RankUpdateRequestDto;
import io.pp.arcade.v1.domain.rank.Rank;
import io.pp.arcade.v1.domain.rank.RankRepository;

import io.pp.arcade.v1.domain.user.User;
import io.pp.arcade.v1.domain.user.UserRepository;
import io.pp.arcade.v1.domain.user.dto.UserDto;
import io.pp.arcade.v1.global.exception.BusinessException;
import io.pp.arcade.v1.domain.rank.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankService {
    private final RankRepository rankRepository;
    private final UserRepository userRepository;

    @Transactional
    public RankDto findBySeasonIdAndUserId(Integer seasonId, Integer userId) {
        Rank rank = rankRepository.findBySeasonIdAndUserId(seasonId, userId).orElse(null);
        if (rank == null) {
            return null;
        }
        return RankDto.from(rank);
    }
    
    @Transactional
    public VipListResponseDto vipList(UserDto curUser, Integer count, Pageable pageable) {
        Integer pageNum = pageable.getPageNumber() < 1 ? 1 : pageable.getPageNumber() - 1;
        pageable = PageRequest.of(pageNum, count);

        Page<User> userPage = userRepository.findAllByOrderByTotalExpDesc(pageable);
        Integer myRank = userRepository.findExpRankingByIntraId(curUser.getIntraId());

        List<VipUserDto> vipUserList = new ArrayList<>();
        Integer index = (pageable.getPageSize() - 1) * pageable.getPageNumber();
        for (User user : userPage) {
            vipUserList.add(VipUserDto.from(user, ++index));
        }
        return VipListResponseDto.builder()
                .rankList(vipUserList)
                .myRank(myRank)
                .totalPage(userPage.getTotalPages())
                .currentPage(userPage.getNumber())
                .build();
    }


    @Transactional
    public void saveAll(RankSaveAllDto saveAllDto) {
        if (saveAllDto.getSeasonId() == null) {
            throw new BusinessException("{server.internal.error}");
        }
        List<RankRedisDto> rankRedisDtos = saveAllDto.getRankRedisDtos();
        List<Rank> rankList = rankRedisDtos.stream().map(rankRedisDto -> {
            Rank rank = new Rank();
            User user = userRepository.getUserByIntraId(rankRedisDto.getIntraId());
            rank.update(rankRedisDto, user, saveAllDto.getSeasonId());
            return rank;
        }).collect(Collectors.toList());
        if (!Collections.isEmpty(rankList))
            rankRepository.saveAll(rankList);
    }

    @Transactional
    public List<RankDto> findAll() {
        List<Rank> ranks = rankRepository.findAll();
        List<RankDto> rankDtos = ranks.stream().map(RankDto::from).collect(Collectors.toList());
        return rankDtos;
    }

    @Transactional
    public void createRankByAdmin(RankCreateRequestDto createRequestDto) {
        User user = userRepository.findById(createRequestDto.getUserId()).orElseThrow();
        Rank rank = Rank.builder()
                .user(user)
                .seasonId(createRequestDto.getSeasonId())
                .racketType(createRequestDto.getRacketType())
                .ppp(createRequestDto.getPpp())
                .ranking(createRequestDto.getRangking())
                .wins(createRequestDto.getWins())
                .losses(createRequestDto.getLosses())
                .build();
        rankRepository.save(rank);
    }

    @Transactional
    public void updateRankByAdmin(RankUpdateRequestDto updateRequestDto) {
        Rank rank = rankRepository.findById(updateRequestDto.getRankId()).orElseThrow();
        rank.update(updateRequestDto.getPpp(), updateRequestDto.getWins(), updateRequestDto.getLosses());
    }

    @Transactional
    public void deleteRankByAdmin(RankDeleteDto deleteDto) {
        Rank rank = rankRepository.findById(deleteDto.getRankId()).orElseThrow();
        rankRepository.delete(rank);
    }

    @Transactional
    public List<RankDto> findRankByAdmin(Pageable pageable) {
        Page<Rank> ranks = rankRepository.findAllByOrderByIdDesc(pageable);
        List<RankDto> rankDtos = ranks.stream().map(RankDto::from).collect(Collectors.toList());
        return rankDtos;
    }
}