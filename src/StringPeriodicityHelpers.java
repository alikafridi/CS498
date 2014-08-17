public class StringPeriodicityHelpers {
	public static String calculateDirection(double delta_x, double delta_y) {
		if (delta_x == 0) {
			if (delta_y > 0) {
				return "N,";
			}
			else if (delta_y < 0) {
				return "S,";
			}
			else {
				return " ,";
			}
		}
		else {
			double slope = delta_y/delta_x;
			if (slope > .414214 && slope < 2.41421) {
				//Direction = NE or SW
				if (delta_y > 0) {
					return "NE,";
				}
				else {
					return "SW,";
				}
			}
			else if (slope < -.414214 && slope > -2.41421) {
				//Direction = NW or SE
				if (delta_y > 0) {
					return "NW,";
				}
				else {
					return "SE,";
				}
			}
			else if (slope > 2.41421 || slope < -2.41421) {
				//Direction = N or S
				if (delta_y > 0) {
					return "N,";
				}
				else {
					return "S,";
				}
			}
			else if (slope > -.414214 && slope < .414214) {
				//Direction = E or W
				if (delta_x > 0) {
					return "E,";
				}
				else {
					return "W,";
				}
			}
		}
		return " ,";
	}
}