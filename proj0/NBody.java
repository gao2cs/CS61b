public class NBody {
	public static double readRadius(String fileName) {
		In in = new In(fileName);
		int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();
		return secondItemInFile;
	}

	public static Planet[] readPlanets(String fileName) {
		In in = new In(fileName);
		int numberOfPlanets = in.readInt();
		double radius = in.readDouble();
		Planet[] planets = new Planet[numberOfPlanets];
		for (int i = 0; i < numberOfPlanets; ++i) {
			planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
		}
		return planets;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String fileName = args[2];
		Planet[] planets = readPlanets(fileName);
		double radius = readRadius(fileName);
		int numberOfPlanets = planets.length;

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		/*
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (int i = 0; i < numberOfPlanets; ++i) {
			planets[i].draw();
		}
		StdDraw.show();
		*/
		double t = 0.0;
		while (t < T) {
			double[] xForces = new double[numberOfPlanets];
			double[] yForces = new double[numberOfPlanets];
			for (int i = 0; i < numberOfPlanets; ++i) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for (int i = 0; i < numberOfPlanets; ++i) {
				planets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (int i = 0; i < numberOfPlanets; ++i) {
				planets[i].draw();
			}
			StdDraw.show();
			int waitTimeMilliseconds = 10;
			StdDraw.pause(waitTimeMilliseconds);
			t += dt;
		}

		StdOut.printf("%d\n", numberOfPlanets);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < numberOfPlanets; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

	}
}