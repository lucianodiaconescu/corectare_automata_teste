package lucrare_licenta.controllers;

import lucrare_licenta.entities.AnswersEntity;
import lucrare_licenta.entities.QuestionsEntity;
import lucrare_licenta.entities.TestsEntity;
import lucrare_licenta.repositories.AnswersRepository;
import lucrare_licenta.services.AnswersService;
import lucrare_licenta.services.QuestionsService;
import lucrare_licenta.services.TestsService;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ScanTestsController {
    private final LoginController loginController;
    private final TestsService testsService;
    private final QuestionsService questionsService;
    private final AnswersService answersService;
    private final AnswersRepository answersRepository;

    @Autowired
    public ScanTestsController(LoginController loginController, TestsService testsService, QuestionsService questionsService, AnswersService answersService, AnswersRepository answersRepository) {
        this.loginController = loginController;
        this.testsService = testsService;
        this.questionsService = questionsService;
        this.answersService = answersService;
        this.answersRepository = answersRepository;
    }

    @GetMapping("/api/users/scantests")
    public ModelAndView showScanTestsPage() {
        ModelAndView modelAndView = new ModelAndView("scantests");
        String loggedInEmail = loginController.getLoggedInUserEmail();
        modelAndView.addObject("email", loggedInEmail);

        List<TestsEntity> tests = testsService.getAllTestsForUser(loggedInEmail);
        modelAndView.addObject("tests", tests);

        Map<Long, List<QuestionsEntity>> questionsMap = new HashMap<>();
        Map<Long, List<AnswersEntity>> answersMap = new HashMap<>();
        Map<Long, List<String>> correctAnswersMap = new HashMap<>();

        for (TestsEntity test : tests) {
            List<QuestionsEntity> questions = questionsService.getQuestionsForTest(test.getId());
            questionsMap.put(test.getId(), questions);
            List<String> correctAnswers = new ArrayList<>();

            for (QuestionsEntity question : questions) {
                Long questionId = question.getId();

                List<AnswersEntity> answers = answersService.getAnswersForQuestion(questionId);


                for (AnswersEntity answer : answers) {
                    answer.setAnswerLetter(answer.getAnswerLetter());
                    if (answer.isCorrect()) {
                        correctAnswers.add(answer.getAnswerLetter());
                    }
                }

                answersMap.put(questionId, answers);
            }
            correctAnswersMap.put(test.getId(), correctAnswers);
        }

        modelAndView.addObject("questionsMap", questionsMap);
        modelAndView.addObject("answersMap", answersMap);
        modelAndView.addObject("correctAnswersMap", correctAnswersMap);
        return modelAndView;
    }

    @PostMapping("/api/users/scantests/upload")
    public ResponseEntity<String> uploadAndScanImage(@RequestParam("image") MultipartFile image) {
        if (image.isEmpty()) {
            return new ResponseEntity<>("Image is empty", HttpStatus.BAD_REQUEST);
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image.getBytes()));

            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Users\\Luciano\\Desktop\\tessdata");

            String scannedText = tesseract.doOCR(bufferedImage);

            List<Character> characters = new ArrayList<>();
            for (char c : scannedText.toCharArray()) {
                characters.add(c);
            }

            return new ResponseEntity<>(scannedText, HttpStatus.OK);
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
