import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String question;
    String[] options;
    String correctAnswer;

    public Question(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class Quiz {
    private List<Question> questions;
    private int score;
    private int currentQuestionIndex;
    private Scanner scanner;
    private Timer timer;
    private boolean answered;

    public Quiz() {
        this.questions = new ArrayList<>();
        this.score = 0;
        this.currentQuestionIndex = 0;
        this.scanner = new Scanner(System.in);
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void startQuiz() {
        if (questions.isEmpty()) {
            System.out.println("No questions added to the quiz.");
            return;
        }

        Collections.shuffle(questions);

        for (currentQuestionIndex = 0; currentQuestionIndex < questions.size(); currentQuestionIndex++) {
            Question question = questions.get(currentQuestionIndex);
            displayQuestion(question);

            answered = false;
            String userAnswer = getUserInputWithTimeout(30); // 30 seconds

            if (userAnswer != null && userAnswer.equalsIgnoreCase(question.correctAnswer)) {
                score++;
                System.out.println("Correct answer!");
            } else if (userAnswer == null) {
                System.out.println("Time's up! The correct answer is " + question.correctAnswer);
            } else {
                System.out.println("Incorrect answer. The correct answer is " + question.correctAnswer);
            }
        }

        displayResult();
    }

    private void displayQuestion(Question question) {
        System.out.println("Question " + (currentQuestionIndex + 1) + ": " + question.question);
        System.out.println("A) " + question.options[0]);
        System.out.println("B) " + question.options[1]);
        System.out.println("C) " + question.options[2]);
        System.out.println("D) " + question.options[3]);
        System.out.print("Enter your answer: ");
    }

    private String getUserInputWithTimeout(int timeout) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!answered) {
                    System.out.println("\nTime's up!");
                }
            }
        }, timeout * 1000);

        String userAnswer = null;
        if (scanner.hasNextLine()) {
            userAnswer = scanner.nextLine();
            answered = true;
            timer.cancel();
        }

        return userAnswer;
    }

    private void displayResult() {
        System.out.println("Quiz completed!");
        System.out.println("Your score is " + score + " out of " + questions.size());
        System.out.println("Correct answers: " + score);
        System.out.println("Incorrect answers: " + (questions.size() - score));
    }

    public static void main(String[] args) {
        Quiz quiz = new Quiz();

        quiz.addQuestion(new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Rome"}, "A"));
        quiz.addQuestion(new Question("What is the largest planet in our solar system?", new String[]{"Earth", "Saturn", "Jupiter", "Uranus"}, "C"));
        quiz.addQuestion(new Question("What is the smallest country in the world?", new String[]{"Vatican City", "Monaco", "Nauru", "Tuvalu"}, "A"));
        quiz.addQuestion(new Question("What is the longest river in the world?", new String[]{"Nile", "Amazon", "Yangtze", "Mississippi"}, "B"));
        quiz.addQuestion(new Question("Who wrote 'To Kill a Mockingbird'?", new String[]{"Harper Lee", "Mark Twain", "Ernest Hemingway", "F. Scott Fitzgerald"}, "A"));
        quiz.addQuestion(new Question("Which element has the chemical symbol 'O'?", new String[]{"Gold", "Oxygen", "Hydrogen", "Silver"}, "B"));
        quiz.addQuestion(new Question("What is the speed of light?", new String[]{"300,000 km/s", "150,000 km/s", "450,000 km/s", "600,000 km/s"}, "A"));
        quiz.addQuestion(new Question("Who is known as the 'Father of Computers'?", new String[]{"Alan Turing", "Charles Babbage", "Bill Gates", "Steve Jobs"}, "B"));
        quiz.addQuestion(new Question("What is the chemical formula of water?", new String[]{"H2O", "CO2", "NaCl", "O2"}, "A"));
        quiz.addQuestion(new Question("What is the largest mammal?", new String[]{"Elephant", "Blue Whale", "Giraffe", "Great White Shark"}, "B"));

        quiz.startQuiz();
    }
}
