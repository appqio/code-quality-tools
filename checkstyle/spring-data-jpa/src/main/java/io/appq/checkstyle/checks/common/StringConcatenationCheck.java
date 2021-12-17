package io.appq.checkstyle.checks.common;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.LinkedList;
import java.util.List;

/**
 * <code>
 * String a = "My name is " + name + ".";
 * message += "hello";
 * System.out.println("error: " + message);
 * </code>
 *
 * @since 0.2.0
 */
public class StringConcatenationCheck extends AbstractCheck {

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.OBJBLOCK };
    }

    @Override
    public int[] getAcceptableTokens() {
        return this.getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return this.getDefaultTokens();
    }

    @Override
    public void visitToken(final DetailAST ast) {
        final List<DetailAST> plusAsts = this.findChildrenOfType(ast, TokenTypes.PLUS, TokenTypes.PLUS_ASSIGN);

        for (final DetailAST plusAst : plusAsts) {
            if (!this.findChildrenOfType(plusAst, TokenTypes.STRING_LITERAL).isEmpty()) {
                this.log(plusAst, "문자열을 `+` 로 연결하지 마세요");
            }
        }
    }

    private List<DetailAST> findChildrenOfType(final DetailAST tree, final int... types) {
        final List<DetailAST> children = new LinkedList<>();

        DetailAST child = tree.getFirstChild();
        while (child != null) {
            if (isOfType(child, types)) {
                children.add(child);
            } else {
                children.addAll(this.findChildrenOfType(child, types));
            }
            child = child.getNextSibling();
        }

        return children;
    }

    private static boolean isOfType(final DetailAST ast, final int... types) {
        boolean matched = false;
        for (final int type : types) {
            if (ast.getType() == type) {
                matched = true;
                break;
            }
        }

        return matched;
    }
}
