//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ClassesLogic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVHandler {
    public CSVHandler() {
    }

    public void writeStock(String stockName, double openingPrice, double closingPrice, double currentPrice) {
        String filename = stockName + ".csv";
        File file = new File(filename);

        try {
            FileWriter fw = new FileWriter(file, true);

            try {
                BufferedWriter bw = new BufferedWriter(fw);

                try {
                    PrintWriter out = new PrintWriter(bw);

                    try {
                        if (!file.exists()) {
                            out.println("Opening Price,Closing Price,Current Price");
                        }

                        out.println("" + openingPrice + "," + closingPrice + "," + currentPrice);
                    } catch (Throwable var18) {
                        try {
                            out.close();
                        } catch (Throwable var17) {
                            var18.addSuppressed(var17);
                        }

                        throw var18;
                    }

                    out.close();
                } catch (Throwable var19) {
                    try {
                        bw.close();
                    } catch (Throwable var16) {
                        var19.addSuppressed(var16);
                    }

                    throw var19;
                }

                bw.close();
            } catch (Throwable var20) {
                try {
                    fw.close();
                } catch (Throwable var15) {
                    var20.addSuppressed(var15);
                }

                throw var20;
            }

            fw.close();
        } catch (IOException var21) {
            IOException e = var21;
            e.printStackTrace();
        }

    }

    public void writeUser(String username, String email, String password, float balance, boolean isPremium) {
        String filename = "user_information.csv";

        try {
            FileWriter fw = new FileWriter(filename, true);

            try {
                BufferedWriter bw = new BufferedWriter(fw);

                try {
                    PrintWriter out = new PrintWriter(bw);

                    try {
                        out.println(username + "," + email + "," + password + "," + balance + "," + isPremium);
                    } catch (Throwable var15) {
                        try {
                            out.close();
                        } catch (Throwable var14) {
                            var15.addSuppressed(var14);
                        }

                        throw var15;
                    }

                    out.close();
                } catch (Throwable var16) {
                    try {
                        bw.close();
                    } catch (Throwable var13) {
                        var16.addSuppressed(var13);
                    }

                    throw var16;
                }

                bw.close();
            } catch (Throwable var17) {
                try {
                    fw.close();
                } catch (Throwable var12) {
                    var17.addSuppressed(var12);
                }

                throw var17;
            }

            fw.close();
        } catch (IOException var18) {
            IOException e = var18;
            e.printStackTrace();
        }

    }

    public List<String> readCSV(String filename) {
        List<String> records = new ArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            String line;
            try {
                while((line = br.readLine()) != null) {
                    records.add(line);
                }
            } catch (Throwable var7) {
                try {
                    br.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            br.close();
        } catch (IOException var8) {
            IOException e = var8;
            e.printStackTrace();
        }

        return records;
    }

    public void modifyUser(String username, float deposit, boolean isPremium) {
        List<String> records = this.readCSV("user_information.csv");
        List<String> updatedRecords = new ArrayList();

        String record;
        for(Iterator var6 = records.iterator(); var6.hasNext(); updatedRecords.add(record)) {
            record = (String)var6.next();
            String[] fields = record.split(",");
            if (fields[0].equals(username)) {
                float balance = Float.parseFloat(fields[3]) + deposit;
                record = fields[0] + "," + fields[1] + "," + fields[2] + "," + balance + "," + isPremium;
            }
        }

        try {
            FileWriter fw = new FileWriter("user_information.csv", false);

            try {
                BufferedWriter bw = new BufferedWriter(fw);

                try {
                    PrintWriter out = new PrintWriter(bw);

                    try {
                        Iterator var22 = updatedRecords.iterator();

                        while(var22.hasNext()) {
                            record = (String)var22.next();
                            out.println(record);
                        }
                    } catch (Throwable var14) {
                        try {
                            out.close();
                        } catch (Throwable var13) {
                            var14.addSuppressed(var13);
                        }

                        throw var14;
                    }

                    out.close();
                } catch (Throwable var15) {
                    try {
                        bw.close();
                    } catch (Throwable var12) {
                        var15.addSuppressed(var12);
                    }

                    throw var15;
                }

                bw.close();
            } catch (Throwable var16) {
                try {
                    fw.close();
                } catch (Throwable var11) {
                    var16.addSuppressed(var11);
                }

                throw var16;
            }

            fw.close();
        } catch (IOException var17) {
            IOException e = var17;
            e.printStackTrace();
        }

    }

    public static List<String> readFirstColumnFromCsv(String filePath) {
        List<String> firstColumnValues = new ArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;
            try {
                while((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (columns.length > 0) {
                        firstColumnValues.add(columns[0].trim());
                    }
                }
            } catch (Throwable var6) {
                try {
                    br.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            br.close();
        } catch (IOException var7) {
            IOException e = var7;
            e.printStackTrace();
        }

        return firstColumnValues;
    }

    public static List<Float> readFloatValuesFromCsv(String filePath) {
        List<Float> floatValues = new ArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;
            try {
                while((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (columns.length >= 1) {
                        float floatValue = Float.parseFloat(columns[0]);
                        floatValues.add(floatValue);
                    }
                }
            } catch (Throwable var7) {
                try {
                    br.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            br.close();
        } catch (IOException var8) {
            IOException e = var8;
            e.printStackTrace();
        }

        return floatValues;
    }

    public static List<String> readHourMinuteValuesFromCsv(String filePath) {
        List<String> hourMinuteValues = new ArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;
            try {
                while((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (columns.length >= 2) {
                        String[] dateTimeParts = columns[1].split(" ");
                        if (dateTimeParts.length >= 2) {
                            String[] timeParts = dateTimeParts[1].split(":");
                            if (timeParts.length >= 2) {
                                String hourMinute = timeParts[0] + ":" + timeParts[1];
                                hourMinuteValues.add(hourMinute);
                            }
                        }
                    }
                }
            } catch (Throwable var9) {
                try {
                    br.close();
                } catch (Throwable var8) {
                    var9.addSuppressed(var8);
                }

                throw var9;
            }

            br.close();
        } catch (IOException var10) {
            IOException e = var10;
            e.printStackTrace();
        }

        return hourMinuteValues;
    }

    public static boolean checkUserPremiumStatus(String filePath, String targetUser) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] params = line.split(",");
                if (params.length >= 5) { // Assuming the last parameter is at index 4
                    String username = params[0];
                    int lastParam = Integer.parseInt(params[4]);
                    if (username.equals(targetUser)) {
                        return lastParam == 1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Default value if user not found or file read error
    }

}
