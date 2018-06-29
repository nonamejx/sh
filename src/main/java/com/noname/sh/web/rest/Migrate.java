package com.noname.sh.web.rest;

import com.google.common.collect.Lists;
import com.noname.sh.domain.Answer;
import com.noname.sh.domain.Question;
import com.noname.sh.domain.Section;
import com.noname.sh.service.AnswerService;
import com.noname.sh.service.QuestionService;
import com.noname.sh.service.SectionService;
import com.noname.sh.service.dto.AnswerDTO;
import com.noname.sh.service.dto.QuestionDTO;
import com.noname.sh.service.dto.SectionDTO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.internal.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/migrate")
public class Migrate {

    private static final String SEGMENT_BREAK = "_______________________________";
    private final Logger log = LoggerFactory.getLogger(SectionResource.class);

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final SectionService sectionService;

    public Migrate(final AnswerService answerService, final QuestionService questionService, final SectionService sectionService) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.sectionService = sectionService;
    }

    @GetMapping
    public void migrate() throws Exception {
        log.debug("migrate() is called !");
        save(getPartOne());
        save(getPartTwo());
        save(getPartThree());
        save(getPartFour());
        log.debug("migrate() is DONE !");
    }

    private void save(List<Section> sectionList) {
        for (Section section : sectionList) {
            final SectionDTO sectionDTO = sectionService.save(newSectionDTO(section));
            for (Question question : section.getQuestions()) {
                final QuestionDTO questionDTO = questionService.save(newQuestionDTO(question, sectionDTO));
                for (Answer answer : question.getAnswers()) {
                    answerService.save(newAnswerDTO(answer, questionDTO));
                }
            }
        }
    }

    private SectionDTO newSectionDTO(Section section) {
        final SectionDTO dto = new SectionDTO();
        dto.setId(section.getId());
        dto.setText(section.getText());
        dto.setAudioName(section.getAudioName());
        dto.setImageName(section.getImageName());
        dto.setImageTag(section.getImageTag());
        dto.setPartNumber(section.getPartNumber());
        return dto;
    }

    private QuestionDTO newQuestionDTO(Question question, SectionDTO sectionDTO) {
        final QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setSectionId(sectionDTO.getId());
        dto.setSectionText(sectionDTO.getText());
        return dto;
    }

    private AnswerDTO newAnswerDTO(Answer answer, QuestionDTO questionDTO) {
        final AnswerDTO dto = new AnswerDTO();
        dto.setId(answer.getId());
        dto.setTitle(answer.getTitle());
        dto.setCorrectAnswer(answer.isCorrectAnswer());
        dto.setQuestionId(questionDTO.getId());
        dto.setQuestionTitle(questionDTO.getTitle());
        return dto;
    }

    private List<Section> getPartOne() throws Exception {
        final List<Section> section2List = new ArrayList<>();

        final Resource resource = new ClassPathResource("part-1.txt");
        List<String> lines = new ArrayList<>();
        Stream<String> stream = Files.lines(Paths.get(resource.getFile().getAbsolutePath()));

        stream.forEach(s -> {
            if (!"".equals(s) && !"© www.english-test.net".equals(s)) {
                lines.add(s);
            }
        });

        // remove the last line because it's \f
        lines.remove(lines.size() - 1);

        // get question list by dividing the line list
        List<List<String>> questionInfoList = Lists.partition(lines, 8);

        // retrieve question info - 8 lines
        for (int i = 0; i < questionInfoList.size(); i++) {

            final Section section2 = new Section();
            final Question question2 = new Question();
            question2.setTitle(questionInfoList.get(i).get(1)); // todo extract the name of audio and img from this title
            section2.setImageTag(questionInfoList.get(i).get(2));

            final Set<Answer> answer2s = new LinkedHashSet<>();
            answer2s.add(new Answer(questionInfoList.get(i).get(3), false));
            answer2s.add(new Answer(questionInfoList.get(i).get(4), false));
            answer2s.add(new Answer(questionInfoList.get(i).get(5), false));
            answer2s.add(new Answer(questionInfoList.get(i).get(6), false));

            question2.setAnswers(answer2s);

            section2.setText(question2.getTitle());
            section2.setAudioName(question2.getTitle().replace("These answer keys refer to the audio file ", ""));
            section2.setImageName("Q-" + (i + 1) + "-Image-" + (i + 1) + ".png");
            section2.setPartNumber(1L);
            section2.getQuestions().add(question2);

            section2List.add(section2);
        }

        // set answer key
        setAnswerKey(section2List, "part-1-key.txt");

        return section2List;
    }

    private List<Section> getPartTwo() throws Exception {
        final List<Section> section2List = new ArrayList<>();

        final Resource resource = new ClassPathResource("part-2.txt");
        List<String> lines = new ArrayList<>();
        Stream<String> stream = Files.lines(Paths.get(resource.getFile().getAbsolutePath()));

        stream.forEach(s -> {
            if (!"".equals(s) && !"© www.english-test.net".equals(s)
                && !"Photocopiable".equals(s)
                && !"\fTOEIC® based exercises, part II – Transcripts and answer keys".equals(s)
                && !StringUtils.isNumeric(s)) {
                lines.add(s);
            }
        });

        // remove the last line because it's \f
        lines.remove(lines.size() - 1);

        // get question list by dividing the line list
        final List<List<String>> sectionInfoList = Lists.partition(lines, 13);

        // retrieve section info - 13 lines
        sectionInfoList.forEach(sectionInfo -> {
            final Section section2 = new Section();
            section2.setText(sectionInfo.get(0));
            section2.setAudioName(sectionInfo.get(0).replace("These answer keys refer to the audio file ", ""));

            final Set<Question> question2List = new LinkedHashSet<>();
            question2List.add(new Question(sectionInfo.get(1), Collections.asSet(new Answer(sectionInfo.get(2), false), new Answer(sectionInfo.get(3), false), new Answer(sectionInfo.get(4), false))));
            question2List.add(new Question(sectionInfo.get(5), Collections.asSet(new Answer(sectionInfo.get(6), false), new Answer(sectionInfo.get(7), false), new Answer(sectionInfo.get(8), false))));
            question2List.add(new Question(sectionInfo.get(9), Collections.asSet(new Answer(sectionInfo.get(10), false), new Answer(sectionInfo.get(11), false), new Answer(sectionInfo.get(12), false))));

            section2.setQuestions(question2List);
            section2.setPartNumber(2L);
            section2List.add(section2);
        });

        // set answer key
        setAnswerKey(section2List, "part-2-key.txt");

        return section2List;
    }

    private List<Section> getPartThree() throws Exception {
        final List<Section> section2List = new ArrayList<>();

        final Resource resource = new ClassPathResource("part-3-question.txt");
        List<String> lines = new ArrayList<>();
        Stream<String> stream = Files.lines(Paths.get(resource.getFile().getAbsolutePath()));

        stream.forEach(s -> {
            if (!"".equals(s) && !"© www.english-test.net".equals(s)
                && !"Photocopiable".equals(s)
                && !"\fTOEIC® based exercises, part III – Questions".equals(s)
                && !StringUtils.isNumeric(s)) {
                lines.add(s);
            }
        });

        // remove the last line because it's \f
        lines.remove(lines.size() - 1);

        // get question list by dividing the line list
        final List<List<String>> sectionInfoList = Lists.partition(lines, 16);

        // retrieve section info - 16 lines
        convertSectionInfo16LinesToSectionList(section2List, sectionInfoList);

        final String[] sectionTexts = getPartThreeSectionTexts();
        for (int i = 0; i < 200; i++) {
            section2List.get(i).setText(sectionTexts[i]);
            section2List.get(i).setPartNumber(3L);
            if (section2List.get(i).getAudioName() != null) {
                section2List.get(i).setAudioName(section2List.get(i).getAudioName().replace("These questions refer to the audio file ", ""));
            }
        }

        // set answer key
        setAnswerKey(section2List, "part-3-key.txt");

        return section2List;
    }

    private String[] getPartThreeSectionTexts() throws Exception {
        final Resource resource = new ClassPathResource("part-3-answer.txt");
        List<String> lines = new ArrayList<>();
        Stream<String> stream = Files.lines(Paths.get(resource.getFile().getAbsolutePath()));

        stream.forEach(s -> {
            if (s.startsWith("This transcript refers to the audio file")) {
                lines.add(SEGMENT_BREAK);
            } else {
                if (!"".equals(s) && !"© www.english-test.net".equals(s)
                    && !"Photocopiable".equals(s)
                    && !"\fTOEIC® based exercises, part III – Transcripts and answer keys".equals(s)
                    && !"Answer2 keys".equals(s)
                    && !StringUtils.isNumeric(s.charAt(0) + "")
                    && !s.startsWith("(")) {
                    lines.add(s);
                }
            }
        });

        // remove the last line because it's \f
        lines.remove(lines.size() - 1);

        final StringBuilder normalizedText = new StringBuilder();
        lines.forEach(s -> normalizedText.append(s).append("\n"));

        return Arrays.copyOfRange(normalizedText.toString().split(SEGMENT_BREAK), 1, 201);
    }

    private List<Section> getPartFour() throws Exception {
        final List<Section> section2List = new ArrayList<>();

        final Resource resource = new ClassPathResource("part-4-question.txt");
        List<String> lines = new ArrayList<>();
        Stream<String> stream = Files.lines(Paths.get(resource.getFile().getAbsolutePath()));

        stream.forEach(s -> {
            if (!"".equals(s) && !"© www.english-test.net".equals(s)
                && !"Photocopiable".equals(s)
                && !"\fTOEIC® based exercises, part IV – Questions".equals(s)
                && !StringUtils.isNumeric(s)) {
                lines.add(s);
            }
        });

        // remove the last line because it's \f
        lines.remove(lines.size() - 1);

        // get question list by dividing the line list
        final List<List<String>> sectionInfoList = Lists.partition(lines, 16);

        // retrieve section info - 16 lines
        convertSectionInfo16LinesToSectionList(section2List, sectionInfoList);

        // set section's text
        final String[] sectionTexts = getPartFourSectionTexts();
        for (int i = 0; i < 200; i++) {
            section2List.get(i).setText(sectionTexts[i]);
            section2List.get(i).setPartNumber(4L);
            if (section2List.get(i).getAudioName() != null) {
                section2List.get(i).setAudioName(section2List.get(i).getAudioName().replace("These questions refer to the audio file ", ""));
            }
        }

        // set answer key
        setAnswerKey(section2List, "part-4-key.txt");

        return section2List;
    }

    private String[] getPartFourSectionTexts() throws Exception {
        final Resource resource = new ClassPathResource("part-4-answer.txt");
        List<String> lines = new ArrayList<>();
        Stream<String> stream = Files.lines(Paths.get(resource.getFile().getAbsolutePath()));

        stream.forEach(s -> {
            if (s.startsWith("This transcript refers to the audio file")) {
                lines.add(SEGMENT_BREAK);
            } else {
                if (!"".equals(s) && !"© www.english-test.net".equals(s)
                    && !"Photocopiable".equals(s)
                    && !"\fTOEIC® based exercises, part IV – Transcripts and answer keys".equals(s)
                    && !"Answer2 keys".equals(s)
                    && !StringUtils.isNumeric(s.charAt(0) + "")
                    && !s.startsWith("(")) {
                    lines.add(s);
                }
            }
        });

        // remove the last line because it's \f
        lines.remove(lines.size() - 1);

        final StringBuilder normalizedText = new StringBuilder();
        lines.forEach(normalizedText::append);

        return Arrays.copyOfRange(normalizedText.toString().split(SEGMENT_BREAK), 1, 201);
    }

    private void convertSectionInfo16LinesToSectionList(List<Section> section2List, List<List<String>> sectionInfoList) {
        sectionInfoList.forEach(sectionInfo -> {
            final Section section2 = new Section();
            section2.setAudioName(sectionInfo.get(0));

            final Set<Question> question2List = new LinkedHashSet<>();
            question2List.add(new Question(sectionInfo.get(1), new LinkedHashSet<>(Arrays.asList(new Answer(sectionInfo.get(2), false), new Answer(sectionInfo.get(3), false), new Answer(sectionInfo.get(4), false), new Answer(sectionInfo.get(5), false)))));
            question2List.add(new Question(sectionInfo.get(6), new LinkedHashSet<>(Arrays.asList(new Answer(sectionInfo.get(7), false), new Answer(sectionInfo.get(8), false), new Answer(sectionInfo.get(9), false), new Answer(sectionInfo.get(10), false)))));
            question2List.add(new Question(sectionInfo.get(11), new LinkedHashSet<>(Arrays.asList(new Answer(sectionInfo.get(12), false), new Answer(sectionInfo.get(13), false), new Answer(sectionInfo.get(14), false), new Answer(sectionInfo.get(15), false)))));

            section2.setQuestions(question2List);
            section2List.add(section2);
        });
    }

    private void setAnswerKey(final List<Section> sectionList, final String answerKeyFileName) throws IOException {
        if (!CollectionUtils.isEmpty(sectionList)) {
            final Resource resource = new ClassPathResource(answerKeyFileName);
            final List<Question> questionList = sectionList.stream()
                .flatMap(s -> s.getQuestions().stream())
                .collect(Collectors.toList());
            final Stream<String> stream = Files.lines(Paths.get(resource.getFile().getAbsolutePath()));
            final List<String> lines = stream.collect(Collectors.toList());
            if (questionList.size() == lines.size()) {
                for (int i = 0; i < lines.size(); i++) {
                    final String key = lines.get(i);
                    questionList.get(i).getAnswers()
                        .stream()
                        .filter(a -> key.equalsIgnoreCase(a.getTitle().charAt(1) + ""))
                        .findFirst()
                        .ifPresent(a -> a.setCorrectAnswer(true));
                }
            }
        }
    }

}
