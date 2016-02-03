/*******************************************************************************
 * Copyright 2016 Amit Kumar Mondal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.amitinside.tooling.chart;

public class RMatrix {

	private final int columns;
	private final double[][] data;
	private final int rows;

	public RMatrix(final double[][] d) {
		this.rows = d.length;
		this.columns = d[0].length;
		this.data = d;
	}

	public RMatrix(final int prows, final int pcolumns) {
		this.rows = prows;
		this.columns = pcolumns;
		this.data = new double[this.rows][this.columns];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				this.data[i][j] = 0.0D;
			}
		}
	}

	public RMatrix(final int[][] d) {
		this.rows = d.length;
		this.columns = d[0].length;
		this.data = new double[this.rows][this.columns];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				this.data[i][j] = d[i][j];
			}
		}
	}

	public RMatrix add(final RMatrix m2) {
		final RMatrix newMatrix = new RMatrix(this.rows, this.columns);
		if ((this.rows == m2.rows) && (this.columns == m2.columns)) {
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.columns; j++) {
					this.data[i][j] += m2.data[i][j];
				}
			}
		}
		return newMatrix;
	}

	public double getValue(final int r, final int c) {
		return this.data[r][c];
	}

	public RMatrix invert() {
		final int n = this.data.length;
		final double[][] D = new double[n + 1][(2 * n) + 2];
		int j;
		for (int i = 0; i < this.rows; i++) {
			for (j = 0; j < this.columns; j++) {
				D[i + 1][j + 1] = this.data[i][j];
			}
		}
		final int n2 = 2 * n;
		for (int i = 1; i <= n; i++) {
			for (j = 1; j <= n; j++) {
				D[i][j + n] = 0.0D;
			}
			D[i][i + n] = 1.0D;
		}
		for (int i = 1; i <= n; i++) {
			final double alpha = D[i][i];
			if (alpha == 0.0D) {
				break;
			}
			for (j = 1; j <= n2; j++) {
				D[i][j] /= alpha;
			}
			for (int k = 1; k <= n; k++) {
				if ((k - i) != 0) {
					final double beta = D[k][i];
					for (j = 1; j <= n2; j++) {
						D[k][j] -= beta * D[i][j];
					}
				}
			}
		}
		final RMatrix m = new RMatrix(this.rows, this.columns);
		for (int i = 0; i < this.rows; i++) {
			for (j = 0; j < this.columns; j++) {
				m.data[i][j] = D[i + 1][j + 1 + n];
			}
		}
		return m;
	}

	public RMatrix mult(final double v) {
		final RMatrix newMatrix = new RMatrix(this.rows, this.columns);
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				newMatrix.data[i][j] = v * this.data[i][j];
			}
		}
		return newMatrix;
	}

	public RMatrix mult(final RMatrix m2) {
		final RMatrix m1 = this;
		final RMatrix newMatrix = new RMatrix(m1.rows, m2.columns);
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < m2.columns; j++) {
				for (int k = 0; k < this.columns; k++) {
					newMatrix.data[i][j] += this.data[i][k] * m2.data[k][j];
				}
			}
		}
		return newMatrix;
	}

	public void setValue(final int r, final int c, final double v) {
		this.data[r][c] = v;
	}

	public RMatrix transpose() {
		final RMatrix newMatrix = new RMatrix(this.columns, this.rows);
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				newMatrix.data[j][i] = this.data[i][j];
			}
		}
		return newMatrix;
	}
}
