import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.tc33.jheatchart.HeatChart;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;


public class PeriodicBehaviorsHelpers extends JPanel {

	private static int area_size = 56;
	private static double [][] out_data = new double [315][98];

	public static int [][] binary_data;

	public static void main(String[] args) {

		String input_file_name = "xls/M0110.xls";
		int [][] ref_areas = findReferenceAreas(input_file_name);

		convertDataToBinary (ref_areas, input_file_name);

		System.out.println("# of ref points:" + binary_data.length);

		for (int a = 0; a < binary_data.length; a++) {
			
			double[] input = new double[]{
					0.0176,
					-0.0620,
					0.2467,
					0.4599,
					-0.0582,
					0.4694,
					0.0001,
					-0.2873};

			DoubleFFT_1D fftDo = new DoubleFFT_1D(input.length);

			double[] fft = new double[binary_data[0].length * 2];

			System.arraycopy(binary_data[a], 0, fft, 0, binary_data[0].length);
			fftDo.realForwardFull(fft);

			for(double d: fft) {
				System.out.println(d);
			}
			// Need to record the results from the FFT to an excel sheet, with a different column for each excel sheet.
		}

		return ;
	}

	public static int[][] findReferenceAreas(String input_file_name) {

		// getDensityData will return a 2d array with the list of data points
		double [][] data = getDensityData(input_file_name);
		String input_file_short = input_file_name.substring(4,9);
		System.out.println(input_file_short);

		// creates a heat map for the data
		HeatChart map = new HeatChart (data);
		map.setTitle("HeatChart_" + input_file_name);
		map.setXAxisLabel("X Axis");
		map.setYAxisLabel("Y Axis");

		try {
			map.saveToFile(new File("heat_maps/"+input_file_short+"_heat_chart_initial.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "Open the Heat Chart Map. In the next\ndialog enter the number of Ref. Areas");

		String num_input = "";

		while (num_input == null || num_input.length() <= 0) {
			num_input = (String)JOptionPane.showInputDialog(
					null,
					"# of Reference Areas (1-4):",
					"Reference Areas",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					"1");
		}

		int num_ref_areas = Integer.parseInt(num_input);
		int [][] ref_areas = new int [num_ref_areas][3];

		for (int a = 0; a < num_ref_areas; a++) {
			for (int b = 0; b < 3; b++) {
				ref_areas[a][b] = 0;
			}
		}

		double [][] data2 = new double [315][98];

		for (int h = 0; h < 315; h++)
			for (int w = 0; w < 98; w++) {
				data2[h][w] = data[h][w];
				out_data[h][w] = data[h][w];
			}

		for (int i = 0; i < num_ref_areas; i++) {
			for (int h = 0; h < (315-area_size); h++) {
				for (int w = 0; w < (98-area_size); w++) {
					int total_sum = getNeighboringDensity(w, h, data2);

					if (total_sum > ref_areas[i][0]) {
						ref_areas[i][0] = total_sum;
						ref_areas[i][1] = w;
						ref_areas[i][2] = h;

					}

				}
			}
			data2 = eraseRefArea(ref_areas[i][1], ref_areas[i][2], data2);
			System.out.println("Reference Area #: " + i); //ref_areas[i][0]
			System.out.println("Reference Area Density: " + ref_areas[i][0]);
			System.out.println("Reference Area Start X: " + ref_areas[i][1]);
			System.out.println("Reference Area Start Y: " + ref_areas[i][2]);

			// creates a heat map for the data
			HeatChart map2 = new HeatChart (data2);
			map2.setTitle("This is my heat chart title");
			map2.setXAxisLabel("X Axis");
			map2.setYAxisLabel("Y Axis");

			try {
				map2.saveToFile(new File("heat_maps/"+input_file_short+"heat_chart"+(i+1)+".png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ref_areas;
	}

	public static int getNeighboringDensity (int w, int h, double [][] data) {
		int total_sum = 0;
		for (int a = 0; a < area_size; a++) {
			for (int b = 0; b < area_size; b++) {
				total_sum += data[h+a][w+b];
			}
		}
		return total_sum;
	}

	public static double [][] eraseRefArea (int w, int h, double [][] data) {
		for (int a = 0; a < area_size; a++) {
			for (int b = 0; b < area_size; b++) {
				data[h+a][w+b] = 0;
			}
		}
		return data;
	}

	public static double [][] getDensityData(String input_file_name) {
		Workbook wrk1;
		try {
			wrk1 = Workbook.getWorkbook(new File(input_file_name));
			Sheet sheet1 = wrk1.getSheet(0);
			int rows=sheet1.getRows();


			double [][] data = new double [315][98];

			for (int h = 0; h < 315; h++) {
				for (int w = 0; w < 98; w++) {
					data[h][w] = 0;
				}
			}

			for (int i = 1; i < rows; i++) {

				Cell x_cell = sheet1.getCell(1, i);
				Cell y_cell = sheet1.getCell(2, i);

				String x_cell_string = x_cell.getContents();
				String y_cell_string = y_cell.getContents();

				Double x_cell_Double = Double.parseDouble(x_cell_string);
				Double y_cell_Double = Double.parseDouble(y_cell_string);

				int x_int = x_cell_Double.intValue();
				int y_int = y_cell_Double.intValue();

				int x_loc = x_int - 317901;
				int y_loc = y_int - 4367501;

				if (x_loc < 99 && y_loc < 316) {
					data[y_loc][x_loc]++;
				}
				else {
					System.out.println("Data is not in valid length. x: " + x_loc + ". y: " + y_loc);
				}
			}
			return data;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void convertDataToBinary (int [][] ref_areas, String input_file_name) {
		Workbook wrk1;
		String input_file_short = input_file_name.substring(4,9);

		try {
			wrk1 = Workbook.getWorkbook(new File(input_file_name));
			Sheet sheet1 = wrk1.getSheet(0);
			int rows=sheet1.getRows();

			binary_data = new int [ref_areas.length][rows];

			for (int a = 0; a < ref_areas.length; a++) {
				int x_min = ref_areas[a][1];
				int x_max = x_min + area_size;
				int y_min = ref_areas[a][2];
				int y_max = y_min + area_size;

				for (int i = 1; i < rows; i++) {
					Cell x_cell = sheet1.getCell(1, i);
					Cell y_cell = sheet1.getCell(2, i);

					String x_cell_string = x_cell.getContents();
					String y_cell_string = y_cell.getContents();

					Double x_cell_Double = Double.parseDouble(x_cell_string);
					Double y_cell_Double = Double.parseDouble(y_cell_string);

					int x_int = x_cell_Double.intValue();
					int y_int = y_cell_Double.intValue();

					int x_loc = x_int - 317901;
					int y_loc = y_int - 4367501;

					if (x_loc > x_min && x_loc < x_max && y_loc > y_min && y_loc < y_max) {
						binary_data[a][i] = 1;
					}
					else {
						binary_data[a][i] = 0;
					}
				}
			}
			File exlFile = new File("binary_data/" + input_file_short + "_binary_file"+".xls");
			WritableWorkbook writableWorkbook = Workbook
					.createWorkbook(exlFile);

			WritableSheet writableSheet = writableWorkbook.createSheet("Sheet1", 0);

			for (int a = 0; a < ref_areas.length; a++) {

				Label label = new Label (a, 0, "Ref_Area_"+a);

				writableSheet.addCell(label);

				for (int i = 1; i <= rows; i++){
					Number value = new Number (a, i, binary_data[a][i-1]);

					writableSheet.addCell((WritableCell) value);

				}
			}

			//Write and close the workbook
			writableWorkbook.write();
			writableWorkbook.close();
			// save the file
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void analysis () {
		// Fourier Transformations and auto-correlation
	}
}
