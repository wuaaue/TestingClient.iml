package Control;

import Model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class LoadFile {
    private TCPConnection connection;
    public boolean isLoad(File file, TCPConnection connection) {
        this.connection=connection;

        try {
            String str;
            Question question = null;
            List<String> lines = Files.readAllLines(Paths.get(String.valueOf(file)));
            for (String line : lines) {
                str=String.valueOf(line).trim();
                if (!str.equals("")) {

                    if (str.charAt(0) == '#' && question==null) {
                        question = new Question(str.substring(1),new VariantWithManyAnswerTrue());
                    }else if(str.charAt(0) == '*' && question==null){
                        question = new Question(str.substring(1),new VariantWithOneAnswerTrue());
                    }else if(str.charAt(0) == '_' && question==null){
                        question = new Question(str.substring(1),new VariantWithInputAnswer());
                    }else if (str.charAt(0) == '?' && question==null)
                        question = new Question(str.substring(1),new VariantWithOrderAnswer());
                    if (question!=null){
                        if(question.getVatiants().getClass()== VariantWithManyAnswerTrue.class){
                            VariantWithManyAnswerTrue vatiants=(VariantWithManyAnswerTrue)question.getVatiants();
                            if (str.charAt(0) == '=')
                                vatiants.getVariants().add(new Variant(String.valueOf(line).trim().substring(1), true));
                            else if (str.charAt(0) == '-')
                                vatiants.getVariants().add(new Variant(String.valueOf(line).trim().substring(1), false));
                        }
                        if(question.getVatiants().getClass()== VariantWithOneAnswerTrue.class){
                            VariantWithOneAnswerTrue vatiants=(VariantWithOneAnswerTrue)question.getVatiants();
                            if (str.charAt(0) == '=')
                                vatiants.getVariants().add(new Variant(String.valueOf(line).trim().substring(1), true));
                            else if (str.charAt(0) == '-')
                                vatiants.getVariants().add(new Variant(String.valueOf(line).trim().substring(1), false));
                        }
                        if(question.getVatiants().getClass()== VariantWithOrderAnswer.class){
                            VariantWithOrderAnswer vatiants=(VariantWithOrderAnswer)question.getVatiants();
                            if (str.charAt(0) == '=')
                                vatiants.getVariants().add(new Variant(String.valueOf(line).trim().substring(1), true));
                            else if (str.charAt(0) == '-')
                                vatiants.getVariants().add(new Variant(String.valueOf(line).trim().substring(1), false));
                        }
                        if(question.getVatiants().getClass()== VariantWithInputAnswer.class){
                            VariantWithInputAnswer vatiants=(VariantWithInputAnswer)question.getVatiants();
                            vatiants.setVariant(new Variant(str.substring(1),true));
                        }
                    }

                }else if(question!=null){
                    question.setId(Math.round(Math.random() * 1000 + System.currentTimeMillis()));
                    connection.sendObject(new AddRecord<Question>(question));
//                    System.out.println(question.toString());
                    question = null;
                }
            }
            if(question!=null){
                    question.setId(Math.round(Math.random() * 1000 + System.currentTimeMillis()));
                    connection.sendObject(new AddRecord<Question>(question));
//                System.out.println(question.toString());
                question = null;
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}



