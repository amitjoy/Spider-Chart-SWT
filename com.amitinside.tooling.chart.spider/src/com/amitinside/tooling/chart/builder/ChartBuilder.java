package com.amitinside.tooling.chart.builder;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.amitinside.tooling.chart.gc.swt.SwtGraphicsProvider;
import com.amitinside.tooling.chart.swt.ChartViewer;

public class ChartBuilder {

	public static ChartViewer viewer(final Composite parent, final Consumer<ChartBuilder> chartBuilderConsumer) {
		final ChartBuilder chartViewerBuilder = new ChartBuilder(parent);
		chartBuilderConsumer.accept(chartViewerBuilder);
		return chartViewerBuilder.chartViewer;
	}

	private final ChartViewer chartViewer;

	public ChartBuilder(final Composite parent) {
		SwtGraphicsProvider.setDefaultDisplay(parent.getShell().getDisplay());
		this.chartViewer = new ChartViewer(parent, SWT.NONE);
		this.chartViewer.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		this.chartViewer.setBounds(parent.getShell().getClientArea().x, parent.getShell().getClientArea().y,
				parent.getShell().getClientArea().width, parent.getShell().getClientArea().height - 20);
	}

	public void chart(final Consumer<DataBuilder> dataBuilderConsumer) {
		final DataBuilder databuilder = new DataBuilder();
		dataBuilderConsumer.accept(databuilder);
	}

}
