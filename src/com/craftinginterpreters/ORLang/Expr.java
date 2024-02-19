package com.craftinginterpreters.ORLang;

import java.util.List;

abstract class Expr {
	interface Visitor<R> {
		R visitAssignExpr (Assign expr);
		R visitBinaryExpr (Binary expr);
		R visitGroupingExpr (Grouping expr);
		R visitLiteralExpr (Literal expr);
		R visitUnaryExpr (Unary expr);
		R visitConditionExpr (Condition expr);
		R visitNothingExpr (Nothing expr);
		R visitVariableExpr (Variable expr);
	}
	static class Assign extends Expr {
		Assign(Token name, Expr value) {
			this.name = name;
			this.value = value;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitAssignExpr(this);
		}

		final Token name;
		final Expr value;
	}

	static class Binary extends Expr {
		Binary(Expr left, Token operator, Expr right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBinaryExpr(this);
		}

		final Expr left;
		final Token operator;
		final Expr right;
	}

	static class Grouping extends Expr {
		Grouping(Expr expression) {
			this.expression = expression;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitGroupingExpr(this);
		}

		final Expr expression;
	}

	static class Literal extends Expr {
		Literal(Object value) {
			this.value = value;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitLiteralExpr(this);
		}

		final Object value;
	}

	static class Unary extends Expr {
		Unary(Token operator, Expr right) {
			this.operator = operator;
			this.right = right;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitUnaryExpr(this);
		}

		final Token operator;
		final Expr right;
	}

	static class Condition extends Expr {
		Condition(Expr condition, Expr trueStatement, Expr falseStatement) {
			this.condition = condition;
			this.trueStatement = trueStatement;
			this.falseStatement = falseStatement;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitConditionExpr(this);
		}

		final Expr condition;
		final Expr trueStatement;
		final Expr falseStatement;
	}

	static class Nothing extends Expr {
		Nothing(String nothing) {
			this.nothing = nothing;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitNothingExpr(this);
		}

		final String nothing;
	}

	static class Variable extends Expr {
		Variable(Token name) {
			this.name = name;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitVariableExpr(this);
		}

		final Token name;
	}


	abstract <R> R accept(Visitor<R> visitor);
}
