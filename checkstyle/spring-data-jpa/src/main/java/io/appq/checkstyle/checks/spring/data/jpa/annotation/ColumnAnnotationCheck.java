package io.appq.checkstyle.checks.spring.data.jpa.annotation;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ColumnAnnotationCheck extends AbstractCheck {

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
                TokenTypes.ANNOTATION,
        };
    }

    @Override
    public void visitToken(final DetailAST annotation) {
        DetailAST child = annotation.getFirstChild();

        boolean isColumnAnnotation = false;
        boolean hasName = false;
        boolean hasNullable = false;
        while (child != null) {
            DetailAST arrayInit = null;

            //System.out.println("child: " + child.getText() + "~ " + child.getType());


            if (child.getType() == TokenTypes.IDENT) { //Annotation 이름
                if (child.getText().equals("Column")) {
                    isColumnAnnotation = true;
                }
                arrayInit = child.findFirstToken(TokenTypes.ANNOTATION_ARRAY_INIT);
            }
            else if (child.getType() == TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR) { //property 를 만났습니다.
                if (isColumnAnnotation && child.getFirstChild().getText().equals("name")) {
                    hasName = true;
                }
                else if (isColumnAnnotation && child.getFirstChild().getText().equals("nullable")) {
                    hasNullable = true;
                }
            }

            child = child.getNextSibling();
        }

        if (isColumnAnnotation) {
            if (!hasName) {
                log(annotation, "@Column 에는 'name' 속성을 반드시 지정해야 합니다.");
            }
            if (!hasNullable) {
                log(annotation, "@Column 에는 'nullable' 속성을 반드시 지정해야 합니다.");
            }
        }
    }
}
