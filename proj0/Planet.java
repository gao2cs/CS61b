public class Planet {

	public double xxPos, yyPos, xxVel, yyVel, mass;
	public String imgFileName;

	private static final double G = 6.67e-11;

	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Planet other) {
		return Math.sqrt(Math.pow(this.xxPos - other.xxPos, 2) + Math.pow(this.yyPos - other.yyPos, 2));
	}

	public double calcForceExertedBy(Planet other) {
		return G * this.mass * other.mass / Math.pow(calcDistance(other), 2);
	}

	public double calcForceExertedByX(Planet other) {
		double dx = other.xxPos - this.xxPos;
		if (dx == 0.0) {
			return 0.0;
		} else {
			return calcForceExertedBy(other) * dx / calcDistance(other);
		}
	}

	public double calcForceExertedByY(Planet other) {
		double dy = other.yyPos - this.yyPos;
		if (dy == 0.0) {
			return 0.0;
		} else {
			return calcForceExertedBy(other) * dy / calcDistance(other);
		}
	}

	public double calcNetForceExertedByX(Planet[] others) {
		double sum = 0.0;
		for (int i = 0; i < others.length; ++i) {
			if (this.equals(others[i])) {
				continue;
			}
			sum += calcForceExertedByX(others[i]);
		}
		return sum;
	}

	public double calcNetForceExertedByY(Planet[] others) {
		double sum = 0.0;
		for (int i = 0; i < others.length; ++i) {
			if (this.equals(others[i])) {
				continue;
			}
			sum += calcForceExertedByY(others[i]);
		}
		return sum;
	}

	public void update(double dt, double fX, double fY) {
		double xxAcel = fX / this.mass;
		double yyAcel = fY / this.mass;

		this.xxVel += dt * xxAcel;
		this.yyVel += dt * yyAcel;

		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}

	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}
	
}