README.md
==========

Class Information
-----------------

* **Analyzer** - Creates an xls file with the data's location, speed, and direction and then highlights times where the same actions was taking place at the same time over multiple days.
* **GenerateData** - Creates synthetic data
* **JCanvas** - Used to visualize the data. Class is written by Mads Rosendahl, University of Roskilde, Denmark.
* **PeriodicBehaviors** - Finds reference (densest) areas, creates a binary stream, and attempts to find periodicity.
* **PeriodicBehaviorsHelpers** - Helper methods for class described above
* **ReferenceDistance** - Calculates the reference point (most dense point in data), and then calculates the distance of the data's location at each point from the point. Outputs the results to an xls file.
* **StringPeriodicity** - Attempts to find substring repetitions in the directions of the data movements. 
* **StringPeriodicityHelpers** - Helper methods for class described above
* **VisualizeData** - Uses JCanvas to create a visual representation of the data
* **XlsxtoXls** - Converts .xlsx files to .xls files


Testing
--------
In order to test this code, add the input files to a folder called "xls".