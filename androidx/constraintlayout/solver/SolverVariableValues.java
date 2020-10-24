package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.ArrayRow;
import java.io.PrintStream;
import java.util.Arrays;

public class SolverVariableValues implements ArrayRow.ArrayRowVariables {
    private static final boolean DEBUG = false;
    private static final boolean HASH = true;
    private static float epsilon = 0.001f;
    private int HASH_SIZE = 16;
    private final int NONE = -1;
    private int SIZE = 16;
    int head = -1;
    int[] keys = new int[16];
    protected final Cache mCache;
    int mCount = 0;
    private final ArrayRow mRow;
    int[] next = new int[16];
    int[] nextKeys = new int[16];
    int[] previous = new int[16];
    float[] values = new float[16];
    int[] variables = new int[16];

    public int sizeInBytes() {
        return 0;
    }

    SolverVariableValues(ArrayRow arrayRow, Cache cache) {
        this.mRow = arrayRow;
        this.mCache = cache;
        clear();
    }

    public int getCurrentSize() {
        return this.mCount;
    }

    public SolverVariable getVariable(int i) {
        int i2 = this.mCount;
        if (i2 == 0) {
            return null;
        }
        int i3 = this.head;
        for (int i4 = 0; i4 < i2; i4++) {
            if (i4 == i && i3 != -1) {
                return this.mCache.mIndexedVariables[this.variables[i3]];
            }
            i3 = this.next[i3];
            if (i3 == -1) {
                break;
            }
        }
        return null;
    }

    public float getVariableValue(int i) {
        int i2 = this.mCount;
        int i3 = this.head;
        for (int i4 = 0; i4 < i2; i4++) {
            if (i4 == i) {
                return this.values[i3];
            }
            i3 = this.next[i3];
            if (i3 == -1) {
                return 0.0f;
            }
        }
        return 0.0f;
    }

    public boolean contains(SolverVariable solverVariable) {
        if (indexOf(solverVariable) != -1) {
            return HASH;
        }
        return false;
    }

    public int indexOf(SolverVariable solverVariable) {
        if (!(this.mCount == 0 || solverVariable == null)) {
            int i = solverVariable.id;
            int i2 = this.keys[i % this.HASH_SIZE];
            if (i2 == -1) {
                return -1;
            }
            if (this.variables[i2] == i) {
                return i2;
            }
            while (true) {
                int[] iArr = this.nextKeys;
                if (iArr[i2] == -1 || this.variables[iArr[i2]] == i) {
                    int[] iArr2 = this.nextKeys;
                } else {
                    i2 = iArr[i2];
                }
            }
            int[] iArr22 = this.nextKeys;
            if (iArr22[i2] != -1 && this.variables[iArr22[i2]] == i) {
                return iArr22[i2];
            }
        }
        return -1;
    }

    public float get(SolverVariable solverVariable) {
        int indexOf = indexOf(solverVariable);
        if (indexOf != -1) {
            return this.values[indexOf];
        }
        return 0.0f;
    }

    public void display() {
        int i = this.mCount;
        System.out.print("{ ");
        for (int i2 = 0; i2 < i; i2++) {
            SolverVariable variable = getVariable(i2);
            if (variable != null) {
                PrintStream printStream = System.out;
                printStream.print(variable + " = " + getVariableValue(i2) + " ");
            }
        }
        System.out.println(" }");
    }

    public String toString() {
        String str;
        String str2;
        String str3 = hashCode() + " { ";
        int i = this.mCount;
        for (int i2 = 0; i2 < i; i2++) {
            SolverVariable variable = getVariable(i2);
            if (variable != null) {
                String str4 = str3 + variable + " = " + getVariableValue(i2) + " ";
                int indexOf = indexOf(variable);
                String str5 = str4 + "[p: ";
                if (this.previous[indexOf] != -1) {
                    str = str5 + this.mCache.mIndexedVariables[this.variables[this.previous[indexOf]]];
                } else {
                    str = str5 + "none";
                }
                String str6 = str + ", n: ";
                if (this.next[indexOf] != -1) {
                    str2 = str6 + this.mCache.mIndexedVariables[this.variables[this.next[indexOf]]];
                } else {
                    str2 = str6 + "none";
                }
                str3 = str2 + "]";
            }
        }
        return str3 + " }";
    }

    public void clear() {
        int i = this.mCount;
        for (int i2 = 0; i2 < i; i2++) {
            SolverVariable variable = getVariable(i2);
            if (variable != null) {
                variable.removeFromRow(this.mRow);
            }
        }
        for (int i3 = 0; i3 < this.SIZE; i3++) {
            this.variables[i3] = -1;
            this.nextKeys[i3] = -1;
        }
        for (int i4 = 0; i4 < this.HASH_SIZE; i4++) {
            this.keys[i4] = -1;
        }
        this.mCount = 0;
        this.head = -1;
    }

    private void increaseSize() {
        int i = this.SIZE * 2;
        this.variables = Arrays.copyOf(this.variables, i);
        this.values = Arrays.copyOf(this.values, i);
        this.previous = Arrays.copyOf(this.previous, i);
        this.next = Arrays.copyOf(this.next, i);
        this.nextKeys = Arrays.copyOf(this.nextKeys, i);
        for (int i2 = this.SIZE; i2 < i; i2++) {
            this.variables[i2] = -1;
            this.nextKeys[i2] = -1;
        }
        this.SIZE = i;
    }

    private void addToHashMap(SolverVariable solverVariable, int i) {
        int[] iArr;
        int i2 = solverVariable.id % this.HASH_SIZE;
        int[] iArr2 = this.keys;
        int i3 = iArr2[i2];
        if (i3 == -1) {
            iArr2[i2] = i;
        } else {
            while (true) {
                iArr = this.nextKeys;
                if (iArr[i3] == -1) {
                    break;
                }
                i3 = iArr[i3];
            }
            iArr[i3] = i;
        }
        this.nextKeys[i] = -1;
    }

    private void displayHash() {
        for (int i = 0; i < this.HASH_SIZE; i++) {
            if (this.keys[i] != -1) {
                String str = hashCode() + " hash [" + i + "] => ";
                int i2 = this.keys[i];
                boolean z = false;
                while (!z) {
                    str = str + " " + this.variables[i2];
                    int[] iArr = this.nextKeys;
                    if (iArr[i2] != -1) {
                        i2 = iArr[i2];
                    } else {
                        z = HASH;
                    }
                }
                System.out.println(str);
            }
        }
    }

    private void removeFromHashMap(SolverVariable solverVariable) {
        int i = solverVariable.id % this.HASH_SIZE;
        int i2 = this.keys[i];
        if (i2 != -1) {
            int i3 = solverVariable.id;
            if (this.variables[i2] == i3) {
                int[] iArr = this.keys;
                int[] iArr2 = this.nextKeys;
                iArr[i] = iArr2[i2];
                iArr2[i2] = -1;
                return;
            }
            while (true) {
                int[] iArr3 = this.nextKeys;
                if (iArr3[i2] == -1 || this.variables[iArr3[i2]] == i3) {
                    int[] iArr4 = this.nextKeys;
                    int i4 = iArr4[i2];
                } else {
                    i2 = iArr3[i2];
                }
            }
            int[] iArr42 = this.nextKeys;
            int i42 = iArr42[i2];
            if (i42 != -1 && this.variables[i42] == i3) {
                iArr42[i2] = iArr42[i42];
                iArr42[i42] = -1;
            }
        }
    }

    private void addVariable(int i, SolverVariable solverVariable, float f) {
        this.variables[i] = solverVariable.id;
        this.values[i] = f;
        this.previous[i] = -1;
        this.next[i] = -1;
        solverVariable.addToRow(this.mRow);
        solverVariable.usageInRowCount++;
        this.mCount++;
    }

    private int findEmptySlot() {
        for (int i = 0; i < this.SIZE; i++) {
            if (this.variables[i] == -1) {
                return i;
            }
        }
        return -1;
    }

    private void insertVariable(int i, SolverVariable solverVariable, float f) {
        int findEmptySlot = findEmptySlot();
        addVariable(findEmptySlot, solverVariable, f);
        if (i != -1) {
            this.previous[findEmptySlot] = i;
            int[] iArr = this.next;
            iArr[findEmptySlot] = iArr[i];
            iArr[i] = findEmptySlot;
        } else {
            this.previous[findEmptySlot] = -1;
            if (this.mCount > 0) {
                this.next[findEmptySlot] = this.head;
                this.head = findEmptySlot;
            } else {
                this.next[findEmptySlot] = -1;
            }
        }
        int[] iArr2 = this.next;
        if (iArr2[findEmptySlot] != -1) {
            this.previous[iArr2[findEmptySlot]] = findEmptySlot;
        }
        addToHashMap(solverVariable, findEmptySlot);
    }

    public void put(SolverVariable solverVariable, float f) {
        float f2 = epsilon;
        if (f <= (-f2) || f >= f2) {
            if (this.mCount == 0) {
                addVariable(0, solverVariable, f);
                addToHashMap(solverVariable, 0);
                this.head = 0;
                return;
            }
            int indexOf = indexOf(solverVariable);
            if (indexOf != -1) {
                this.values[indexOf] = f;
                return;
            }
            if (this.mCount + 1 >= this.SIZE) {
                increaseSize();
            }
            int i = this.mCount;
            int i2 = this.head;
            int i3 = -1;
            for (int i4 = 0; i4 < i; i4++) {
                if (this.variables[i2] == solverVariable.id) {
                    this.values[i2] = f;
                    return;
                }
                if (this.variables[i2] < solverVariable.id) {
                    i3 = i2;
                }
                i2 = this.next[i2];
                if (i2 == -1) {
                    break;
                }
            }
            insertVariable(i3, solverVariable, f);
            return;
        }
        remove(solverVariable, HASH);
    }

    public float remove(SolverVariable solverVariable, boolean z) {
        int indexOf = indexOf(solverVariable);
        if (indexOf == -1) {
            return 0.0f;
        }
        removeFromHashMap(solverVariable);
        float f = this.values[indexOf];
        if (this.head == indexOf) {
            this.head = this.next[indexOf];
        }
        this.variables[indexOf] = -1;
        int[] iArr = this.previous;
        if (iArr[indexOf] != -1) {
            int[] iArr2 = this.next;
            iArr2[iArr[indexOf]] = iArr2[indexOf];
        }
        int[] iArr3 = this.next;
        if (iArr3[indexOf] != -1) {
            int[] iArr4 = this.previous;
            iArr4[iArr3[indexOf]] = iArr4[indexOf];
        }
        this.mCount--;
        solverVariable.usageInRowCount--;
        if (z) {
            solverVariable.removeFromRow(this.mRow);
        }
        return f;
    }

    public void add(SolverVariable solverVariable, float f, boolean z) {
        float f2 = epsilon;
        if (f <= (-f2) || f >= f2) {
            int indexOf = indexOf(solverVariable);
            if (indexOf == -1) {
                put(solverVariable, f);
                return;
            }
            float[] fArr = this.values;
            fArr[indexOf] = fArr[indexOf] + f;
            float f3 = fArr[indexOf];
            float f4 = epsilon;
            if (f3 > (-f4) && fArr[indexOf] < f4) {
                fArr[indexOf] = 0.0f;
                remove(solverVariable, z);
            }
        }
    }

    public float use(ArrayRow arrayRow, boolean z) {
        float f = get(arrayRow.variable);
        remove(arrayRow.variable, z);
        SolverVariableValues solverVariableValues = (SolverVariableValues) arrayRow.variables;
        int currentSize = solverVariableValues.getCurrentSize();
        int i = solverVariableValues.head;
        int i2 = 0;
        int i3 = 0;
        while (i2 < currentSize) {
            if (solverVariableValues.variables[i3] != -1) {
                add(this.mCache.mIndexedVariables[solverVariableValues.variables[i3]], solverVariableValues.values[i3] * f, z);
                i2++;
            }
            i3++;
        }
        return f;
    }

    public void invert() {
        int i = this.mCount;
        int i2 = this.head;
        int i3 = 0;
        while (i3 < i) {
            float[] fArr = this.values;
            fArr[i2] = fArr[i2] * -1.0f;
            i2 = this.next[i2];
            if (i2 != -1) {
                i3++;
            } else {
                return;
            }
        }
    }

    public void divideByAmount(float f) {
        int i = this.mCount;
        int i2 = this.head;
        int i3 = 0;
        while (i3 < i) {
            float[] fArr = this.values;
            fArr[i2] = fArr[i2] / f;
            i2 = this.next[i2];
            if (i2 != -1) {
                i3++;
            } else {
                return;
            }
        }
    }
}
