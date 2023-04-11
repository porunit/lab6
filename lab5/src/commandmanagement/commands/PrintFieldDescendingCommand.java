package commandmanagement.commands;

import commandmanagement.Command;
import data.comparators.FormOfEducationComparator;
import data.FormOfEducation;
import executionmanager.CollectionManager;
import Client.io.OutputHandler;

import java.util.ArrayList;

@NoArguments
public class PrintFieldDescendingCommand extends Command {

    /**
     * Action for <b>print_field_descending_form_of_education</b> command.
     * Doesn't receive arguments
     */
    public void execute(String argument, OutputHandler outputHandler) {
        var groups = CollectionManager.getAll();
        ArrayList<FormOfEducation> formOfEducations = new ArrayList<>();
        for (var it : groups) {
            formOfEducations.add(it.getFormOfEducation());
        }
        formOfEducations.sort(new FormOfEducationComparator());
        StringBuilder builder = new StringBuilder();
        for (var it : formOfEducations) {
            builder.append(it).append(" | ");
        }
        outputHandler.print(builder.toString());
    }

    @Override
    public String getDescription() {
        return "print_field_descending_form_of_education : вывести значения поля formOfEducation всех элементов в порядке убывания";
    }
}
