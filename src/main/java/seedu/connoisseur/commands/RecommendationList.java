package seedu.connoisseur.commands;

import seedu.connoisseur.exceptions.DuplicateException;
import seedu.connoisseur.exceptions.EmptyInputException;
import seedu.connoisseur.recommendation.Recommendation;
import seedu.connoisseur.review.Review;
import seedu.connoisseur.storage.ConnoisseurData;
import seedu.connoisseur.ui.Ui;

import java.util.ArrayList;

import static seedu.connoisseur.messages.Messages.CHANGE_RECO_TITLE;
import static seedu.connoisseur.messages.Messages.DUPLICATE_RECOMMENDATION;
import static seedu.connoisseur.messages.Messages.RECO_TITLE_PROMPT;
import static seedu.connoisseur.messages.Messages.CATEGORY_PROMPT;
import static seedu.connoisseur.messages.Messages.PRICE_PROMPT;
import static seedu.connoisseur.messages.Messages.INVALID_COMMAND;
import static seedu.connoisseur.messages.Messages.ADD_SUCCESS;
import static seedu.connoisseur.messages.Messages.EDIT_RECBY_PROMPT;
import static seedu.connoisseur.messages.Messages.EDIT_CATEGORY_PROMPT;
import static seedu.connoisseur.messages.Messages.EDIT_LOCATION_PROMPT;
import static seedu.connoisseur.messages.Messages.EDIT_RANGE_PROMPT;
import static seedu.connoisseur.messages.Messages.RECOBY_PROMPT;
import static seedu.connoisseur.messages.Messages.LOCATION_PROMPT;
import static seedu.connoisseur.messages.Messages.MISSING_DELETE_TITLE;
import static seedu.connoisseur.messages.Messages.INVALID_DELETE_RECO_TITLE;
import static seedu.connoisseur.messages.Messages.DELETE_SUCCESS;
import static seedu.connoisseur.messages.Messages.RATING_PROMPT;
import static seedu.connoisseur.messages.Messages.DETAILS_PROMPT;
import static seedu.connoisseur.messages.Messages.ENTER_DETAILS_PROMPT;
import static seedu.connoisseur.messages.Messages.CONVERT_SUCCESS;
import static seedu.connoisseur.messages.Messages.MISSING_EDIT_TITLE;
import static seedu.connoisseur.messages.Messages.EDIT_PROMPT_RECO;
import static seedu.connoisseur.messages.Messages.ANYTHING_ELSE;
import static seedu.connoisseur.messages.Messages.EDIT_TITLE_PROMPT;
import static seedu.connoisseur.messages.Messages.ABANDON_RECO;

/**
 * Class with methods for different commands in recommendation mode.
 */
public class RecommendationList {
    private final Ui ui;
    public ArrayList<Recommendation> recommendations = new ArrayList<>();
    private ReviewList reviewList;

    /**
     * Constructor for RecommendationList with stored data.
     *
     * @param connoisseurData locally stored data
     * @param ui              instance of ui for user interaction
     * @param reviewList      instance of reviewlist
     */
    public RecommendationList(ConnoisseurData connoisseurData, Ui ui, ReviewList reviewList) {
        this.ui = ui;
        this.recommendations = connoisseurData.getRecommendations();
        this.reviewList = reviewList;
    }

    /**
     * Constructor for RecommendationList without stored data.
     *
     * @param ui         instance of ui for user interaction
     * @param reviewList instance of reviewlist
     */
    public RecommendationList(Ui ui, ReviewList reviewList) {
        this.reviewList = reviewList;
        this.ui = ui;
    }

    /**
     * List reviews according to different types of input.
     */
    public void listRecommendations() {
        if (recommendations.size() == 0) {
            ui.printEmptyRecommendationListMessage();
        } else {
            displayRecommendations(recommendations);
        }
    }

    /**
     * Displays the recommendations.
     */
    public void displayRecommendations(ArrayList<Recommendation> recommendationList) {
        ui.printRecommendationListHeading();
        for (int i = 0; i < recommendationList.size(); i++) {
            Recommendation currentRecommendation = recommendationList.get(i);
            ui.print("| " + (i + 1) + ". ");
            if (i < 9) {
                ui.print(" ");
            }
            ui.print(currentRecommendation.getTitle());
            ui.printWhiteSpaceTitle(currentRecommendation.getTitle().length());
            ui.print("| " + currentRecommendation.getCategory());
            ui.printWhiteSpace(currentRecommendation.getCategory().length());
            ui.print("| " + currentRecommendation.priceRange());
            ui.printWhiteSpace(currentRecommendation.priceRange().length() - 2);
            ui.print("| " + currentRecommendation.getLocation());
            ui.printWhiteSpace(currentRecommendation.getLocation().length());
            ui.print("| " + currentRecommendation.getRecommendedBy());
            ui.printWhiteSpace(currentRecommendation.getRecommendedBy().length());
            ui.println("|");
        }
        ui.printTableEndBorderForReco();
    }

    /**
     * Checks for duplicate recommendation.
     *
     * @param title title of recommendation.
     */
    public boolean checkAndPrintDuplicateRecommendation(String title) {
        int recIndex = -1;
        for (int i = 0; i < recommendations.size(); i++) {
            if ((recommendations.get(i).getTitle().toLowerCase()).compareTo(title.toLowerCase()) == 0) {
                recIndex = i;
            }
        }
        if (recIndex == -1) {
            return false;
        }
        ui.println(DUPLICATE_RECOMMENDATION);
        Recommendation currentRecommendation = recommendations.get(recIndex);
        ui.print((recommendations.indexOf(currentRecommendation) + 1) + ". ");
        if (recommendations.indexOf(currentRecommendation) < 9) {
            ui.print(" ");
        }
        ui.print(currentRecommendation.getTitle());
        ui.printWhiteSpace(currentRecommendation.getTitle().length());
        ui.print(currentRecommendation.getCategory());
        ui.printWhiteSpace(currentRecommendation.getCategory().length());
        ui.print(currentRecommendation.priceRange());
        ui.printWhiteSpace(currentRecommendation.priceRange().length());
        ui.println(currentRecommendation.getRecommendedBy());
        return true;
    }

    /**
     * Adds a recommendation.
     */
    public void addRecommendation() {
        try {
            addRecommendationDetails();
        } catch (DuplicateException de) {
            ui.printNoUniqueTitleMessage();
        } catch (EmptyInputException ee) {
            ui.printEmptyInputMessage();
        }
    }

    /**
     * Prompts for details of recommendation.
     */
    public void addRecommendationDetails() throws DuplicateException, EmptyInputException {
        String title;
        String input;
        String category;
        double priceLow = 0;
        double priceHigh = 0;
        String recommendedBy;
        String location;
        while (true) {
            ui.println(RECO_TITLE_PROMPT);
            title = ui.readCommand();
            if (checkAndPrintDuplicateRecommendation(title)) {
                ui.printNoUniqueTitleMessage();
                continue;
            }
            if (title.isBlank()) {
                ui.printEmptyInputMessage();
                continue;
            }
            if (title.length() > 20) {
                ui.printInputTooLongMessage_20Char();
                continue;
            }
            if (reviewList.checkAndPrintDuplicateReview(title)) {
                ui.println(CHANGE_RECO_TITLE);
                boolean invalidCommand;
                do {
                    ui.println(ABANDON_RECO);
                    input = ui.readCommand().toLowerCase().trim();
                    if (input.equals("y")) {
                        return;
                    } else if (input.equals("n")) {
                        break;
                    } else {
                        ui.println(INVALID_COMMAND);
                        invalidCommand = true;
                    }
                } while (invalidCommand);

                if (input.equals("n")) {
                    continue;
                }
            }
            break;
        }
        while (true) {
            ui.println(CATEGORY_PROMPT);
            category = ui.readCommand().toLowerCase();
            if (category.isBlank()) {
                ui.printEmptyInputMessage();
                continue;
            }
            if (category.length() > 15) {
                ui.printInputTooLongMessage_15Char();
                continue;
            }
            break;
        }
        while (true) {
            ui.println(PRICE_PROMPT);
            String priceRange = ui.readCommand();
            try {
                double priceFirst = Double.parseDouble(priceRange.split("-", 2)[0].trim());
                double priceSecond = Double.parseDouble(priceRange.split("-", 2)[1].trim());
                if (!checkPriceValidity(priceFirst) || !checkPriceValidity(priceSecond)) {
                    ui.printInvalidPriceRangeMessage();
                    continue;
                }
                if (priceFirst > priceSecond) {
                    priceLow = priceSecond;
                    priceHigh = priceFirst;
                } else {
                    priceLow = priceFirst;
                    priceHigh = priceSecond;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                ui.printInvalidPricingMessage();
                continue;
            }
            break;
        }
        while (true) {
            ui.println(RECOBY_PROMPT);
            recommendedBy = ui.readCommand();
            if (recommendedBy.isBlank()) {
                ui.printEmptyInputMessage();
                continue;
            }
            if (recommendedBy.length() > 15) {
                ui.printInputTooLongMessage_15Char();
                continue;
            }
            break;
        }
        while (true) {
            ui.println(LOCATION_PROMPT);
            location = ui.readCommand();
            if (location.isBlank()) {
                ui.printEmptyInputMessage();
                continue;
            }
            if (location.length() > 15) {
                ui.printInputTooLongMessage_15Char();
                continue;
            }
            break;
        }
        priceLow = Math.round(priceLow * 100.0) / 100.0;
        priceHigh = Math.round(priceHigh * 100.0) / 100.0;
        Recommendation r = new Recommendation(title, category, priceLow, priceHigh, recommendedBy, location);
        recommendations.add(r);
        ui.println(title + ADD_SUCCESS);
    }

    /**
     * Delete review.
     */
    public void deleteRecommendation(String title) {
        if (title == null || title.isBlank()) {
            ui.println(MISSING_DELETE_TITLE);
            return;
        }
        int recommendationIndex = -1;
        for (int i = 0; i < recommendations.size(); i++) {
            if (recommendations.get(i).getTitle().compareToIgnoreCase(title.toLowerCase()) == 0) {
                recommendationIndex = i;
                break;
            }
        }
        if (recommendationIndex == -1) {
            ui.println(INVALID_DELETE_RECO_TITLE);
        } else {
            recommendations.remove(recommendationIndex);
            ui.println(title + DELETE_SUCCESS);
        }
    }

    /**
     * Converts a recommendation to a review.
     *
     * @param title title of recommendation to be converted
     */
    public void convertRecommendation(String title) {
        String category;
        int rating;
        String description;
        if (title == null || title.isBlank()) {
            ui.println(MISSING_DELETE_TITLE);
            return;
        }
        int recommendationIndex = -1;
        for (int i = 0; i < recommendations.size(); i++) {
            if (recommendations.get(i).getTitle().toLowerCase().compareTo(title.toLowerCase()) == 0) {
                recommendationIndex = i;
                break;
            }
        }
        if (recommendationIndex == -1) {
            ui.println(INVALID_DELETE_RECO_TITLE);
        } else {
            category = recommendations.get(recommendationIndex).getCategory();
            description = "No description entered.";
            Review r = new Review(recommendations.get(recommendationIndex).getTitle(), category, 0, description);

            while (true) {
                ui.println(RATING_PROMPT);
                try {
                    rating = Integer.parseInt(ui.readCommand());
                } catch (NumberFormatException e) {
                    ui.printInvalidRatingMessage();
                    continue;
                }
                if (rating < 0 || rating > 5) {
                    ui.printInvalidRatingMessage();
                    continue;
                }
                r.setRating(rating);
                break;
            }
            while (true) {
                ui.println(DETAILS_PROMPT);
                switch (ui.readCommand().toLowerCase()) {
                case "y":
                    String newDescription;
                    while (true) {
                        ui.println(ENTER_DETAILS_PROMPT);
                        newDescription = ui.readCommand();
                        if (newDescription.isBlank()) {
                            ui.printEmptyInputMessage();
                            continue;
                        }
                        break;
                    }
                    r.setDescription(newDescription);
                    break;
                case "n":
                    break;
                default:
                    ui.println(INVALID_COMMAND);
                    continue;
                }
                break;
            }
            reviewList.receiveConvert(r);
            ui.println(recommendations.get(recommendationIndex).getTitle() + CONVERT_SUCCESS);
            recommendations.remove(recommendationIndex);
        }

    }

    /**
     * Edit a recommendation.
     *
     * @param title title of recommendation to be edited
     */
    public void editRecommendation(String title) {
        int index = -1;
        if (title == null || title.isBlank()) {
            ui.println(MISSING_EDIT_TITLE);
            return;
        } else {
            for (int i = 0; i < recommendations.size(); i++) {
                if (recommendations.get(i).getTitle().equalsIgnoreCase(title)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                ui.printInvalidRecommendation();
                return;
            }
        }
        boolean isDoneEditing = false;
        while (!isDoneEditing) {
            do {
                ui.println(EDIT_PROMPT_RECO);
            } while (!editRecommendationFields(index));
            while (true) {
                ui.println(ANYTHING_ELSE);
                String answer = ui.readCommand();
                switch (answer.toLowerCase()) {
                case "y":
                    break;
                case "n":
                    isDoneEditing = true;
                    break;
                default:
                    ui.println(INVALID_COMMAND);
                    continue;
                }
                break;
            }
        }
    }

    /**
     * Edit specific fields of the recommendation.
     *
     * @param index index of the recommendation to be edited
     */
    public boolean editRecommendationFields(int index) {
        String input = ui.readCommand();
        try {
            input = input.trim().toLowerCase();
        } catch (ArrayIndexOutOfBoundsException e) {
            input = null;
        }
        switch (input) {
        case "title":
            String newTitle;
            while (true) {
                ui.println(EDIT_TITLE_PROMPT);
                newTitle = ui.readCommand();
                if (newTitle.isBlank()) {
                    ui.printEmptyInputMessage();
                    continue;
                }
                if (newTitle.length() > 20) {
                    ui.printInputTooLongMessage_20Char();
                    continue;
                }
                break;
            }
            recommendations.get(index).setTitle(newTitle);
            break;
        case "price range":
            double newPriceLow;
            double newPriceHigh;
            while (true) {
                ui.println(EDIT_RANGE_PROMPT);
                String newPriceRange = ui.readCommand();
                try {
                    double priceFirst = Double.parseDouble(newPriceRange.split("-", 2)[0].trim());
                    double priceSecond = Double.parseDouble(newPriceRange.split("-", 2)[1].trim());
                    if (!checkPriceValidity(priceFirst) || !checkPriceValidity(priceSecond)) {
                        ui.printInvalidPriceRangeMessage();
                        continue;
                    }
                    if (priceFirst > priceSecond) {
                        newPriceLow = priceSecond;
                        newPriceHigh = priceFirst;
                    } else {
                        newPriceLow = priceFirst;
                        newPriceHigh = priceSecond;
                    }
                    newPriceLow = Math.round(newPriceLow * 100.0) / 100.0;
                    newPriceHigh = Math.round(newPriceHigh * 100.0) / 100.0;
                    recommendations.get(index).setPriceHigh(newPriceHigh);
                    recommendations.get(index).setPriceLow(newPriceLow);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    ui.printInvalidPricingMessage();
                    continue;
                }
                break;
            }
            break;
        case "location":
            String newLocation;
            while (true) {
                ui.println(EDIT_LOCATION_PROMPT);
                newLocation = ui.readCommand();
                if (newLocation.isBlank()) {
                    ui.printEmptyInputMessage();
                    continue;
                }
                if (newLocation.length() > 15) {
                    ui.printInputTooLongMessage_15Char();
                    continue;
                }
                break;
            }
            recommendations.get(index).setLocation(newLocation);
            break;
        case "category":
            String newCategory;
            while (true) {
                ui.println(EDIT_CATEGORY_PROMPT);
                newCategory = ui.readCommand();
                if (newCategory.isBlank()) {
                    ui.printEmptyInputMessage();
                    continue;
                }
                if (newCategory.length() > 15) {
                    ui.printInputTooLongMessage_15Char();
                    continue;
                }
                break;
            }
            recommendations.get(index).setCategory(newCategory);
            break;
        case "recby":
            String newRecBy;
            while (true) {
                ui.println(EDIT_RECBY_PROMPT);
                newRecBy = ui.readCommand();
                if (newRecBy.isBlank()) {
                    ui.printEmptyInputMessage();
                    continue;
                }
                if (newRecBy.length() > 15) {
                    ui.printInputTooLongMessage_15Char();
                    continue;
                }
                break;
            }
            recommendations.get(index).setRecommendedBy(newRecBy);
            break;
        default:
            ui.println(INVALID_COMMAND);
            return false;
        }
        return true;
    }

    public boolean checkPriceValidity(double price) {
        boolean isValid = false;
        if (price >= 0 && price <= 9999.99) {
            isValid = true;
        }
        return isValid;
    }
}