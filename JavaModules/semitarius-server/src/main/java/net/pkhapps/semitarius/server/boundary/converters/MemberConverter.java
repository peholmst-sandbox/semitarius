package net.pkhapps.semitarius.server.boundary.converters;

import net.pkhapps.semitarius.server.boundary.exception.MemberNotFoundException;
import net.pkhapps.semitarius.server.domain.model.Member;
import net.pkhapps.semitarius.server.domain.model.MemberRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * A Spring converter that converts a string (the {@link Member#getId() member ID}) to a
 * {@link Member}. If the member is not found, a {@link MemberNotFoundException} is thrown. In other words,
 * a REST method that takes a {@link Member} as a parameter will never be called with the parameter being {@code null}.
 */
@Component
class MemberConverter implements Converter<String, Member> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberConverter.class);

    private final MemberRepository memberRepository;

    @Autowired
    MemberConverter(@NotNull MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member convert(String source) {
        final Member member =
                Optional.ofNullable(memberRepository.findOne(Long.valueOf(source)))
                        .orElseThrow(MemberNotFoundException::new);
        LOGGER.trace("Converted [{}] to member [{}]", source, member);
        return member;
    }
}
