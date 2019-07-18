package com.airwallex;

import com.airwallex.calculator.Calculator;
import com.airwallex.calculator.CalculatorContext;
import com.airwallex.common.Constants;
import com.airwallex.data.InputItem;
import com.airwallex.display.DisplayStrategy;
import com.airwallex.exception.OperationException;
import com.airwallex.parser.CommandLineParser;
import com.airwallex.utils.ServiceUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Scanner;

public class Main {


    public static DisplayStrategy getDisplayStrategy() {
        String displayProp = System.getProperty(Constants.SYSTEM_PROPERTIES_DISPLAY_STRATEGY);
        displayProp = StringUtils.isBlank(displayProp) ? Constants.DEFAULT_DISPLAY_STRATEGY_KEY : displayProp;
        return ServiceUtils.getDisplayStrategy(displayProp);
    }

    public static void main(String[] args) {
        try {
            // init components
            CommandLineParser parser = new CommandLineParser();
            Calculator cal = CalculatorContext.getCalculator();
            DisplayStrategy displayStrategy = getDisplayStrategy();
            // begin to scan input from command line
            System.out.println("Please input RPN expression!(Press \"quit\" to exit the program)");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                // read from command line
                String line = scanner.nextLine();
                if (quit(line)) {
                    break;
                }
                // parse command line
                List<InputItem> inputItemList = parser.parseLine(line);
                // calculate
                OperationException error = cal.calculate(inputItemList);
                // display
                displayStrategy.display(cal.getStack(), error);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            CalculatorContext.destroyCalculator();
        }

    }

    private static boolean quit(String input) {
        return input.equalsIgnoreCase("quit");
    }
}
