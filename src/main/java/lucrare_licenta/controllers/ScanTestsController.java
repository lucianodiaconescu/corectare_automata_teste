package lucrare_licenta.controllers;

import lucrare_licenta.entities.AnswersEntity;
import lucrare_licenta.entities.QuestionsEntity;
import lucrare_licenta.entities.TestsEntity;
import lucrare_licenta.services.AnswersService;
import lucrare_licenta.services.QuestionsService;
import lucrare_licenta.services.TestsService;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
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

    @Autowired
    public ScanTestsController(LoginController loginController, TestsService testsService, QuestionsService questionsService, AnswersService answersService) {
        this.loginController = loginController;
        this.testsService = testsService;
        this.questionsService = questionsService;
        this.answersService = answersService;
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

        for (TestsEntity test : tests) {
            List<QuestionsEntity> questions = questionsService.getQuestionsForTest(test.getId());
            questionsMap.put(test.getId(), questions);

            for (QuestionsEntity question : questions) {
                Long questionId = question.getId();

                List<AnswersEntity> answers = answersService.getAnswersForQuestion(questionId);

                for (AnswersEntity answer : answers) {
                    answer.setAnswerLetter(answer.getAnswerLetter());
                }
                answersMap.put(questionId, answers);
            }
        }

        modelAndView.addObject("questionsMap", questionsMap);
        modelAndView.addObject("answersMap", answersMap);

        return modelAndView;
    }

    @PostMapping("/api/users/scan")
    public String processScanImage(@RequestParam("testId") Long testId, @RequestParam("image") MultipartFile imageFile) {
        try {
            // Salvăm fișierul pe disc
            File file = new File(imageFile.getOriginalFilename());
            imageFile.transferTo(file);

            // Încărcăm imaginea utilizând OpenCV
            Mat image = Imgcodecs.imread(file.getAbsolutePath());

            // Convertim imaginea în gri
            Mat grayImage = new Mat();
            Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

            // Aplicăm filtrul Gaussian pentru reducerea zgomotului
            Mat blurredImage = new Mat();
            Imgproc.GaussianBlur(grayImage, blurredImage, new Size(5, 5), 0);

            // Detectăm marginile folosind metoda Canny
            Mat edges = new Mat();
            Imgproc.Canny(blurredImage, edges, 75, 200);

            // Găsim contururile
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(edges, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            // Iterăm prin contururi și le desenăm pe imaginea originală
            for (MatOfPoint contour : contours) {
                // Ignorăm contururile foarte mici
                if (Imgproc.contourArea(contour) > 2500) {
                    Imgproc.drawContours(image, contours, contours.indexOf(contour), new Scalar(0, 255, 0), 2);
                }
            }

            // Salvăm imaginea cu contururile desenate
            String outputImagePath = "processed_image.jpg";
            Imgcodecs.imwrite(outputImagePath, image);

            // Întoarcem calea către imaginea procesată
            return outputImagePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error processing image: " + e.getMessage();
        }
    }
}
