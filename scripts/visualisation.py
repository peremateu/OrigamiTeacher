import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Load the dataset
file_path = "data/processed/likability_dataset.csv"  
data = pd.read_csv(file_path)

# Define metrics and group by responses
columns_of_interest = ["Like_Dislike", "Unfriendly_Friendly", "Unkind_Kind", 
                        "Unpleasant_Pleasant", "Awful_Nice"]

# Prepare data for a diverging stacked bar chart
likert_data = data.melt(id_vars="Group", value_vars=columns_of_interest, 
                        var_name="Metric", value_name="Score")

# Calculate percentages for each rating per group and metric
likert_summary = (
    likert_data.groupby(["Metric", "Group", "Score"])
    .size()
    .reset_index(name="Count")
    .pivot_table(index=["Metric", "Score"], columns="Group", values="Count", fill_value=0)
)

# Normalize counts to percentages
likert_summary["Total"] = likert_summary.sum(axis=1)
likert_summary["Encouragement"] = (likert_summary["Encouragement"] / likert_summary["Total"]) * 100
likert_summary["No Encouragement"] = -(likert_summary["No Encouragement"] / likert_summary["Total"]) * 100

# Plot diverging stacked bar chart for each metric
for metric in columns_of_interest:
    metric_data = likert_summary.xs(metric, level="Metric").reset_index()
    plt.figure(figsize=(10, 6))
    plt.barh(metric_data["Score"], metric_data["Encouragement"], color="lightblue", label="Encouragement")
    plt.barh(metric_data["Score"], metric_data["No Encouragement"], color="salmon", label="No Encouragement")
    plt.axvline(0, color="black", linewidth=0.8)
    plt.title(f"Diverging Stacked Bar Chart for {metric.replace('_', ' ')}")
    plt.xlabel("Percentage (%)")
    plt.ylabel("Likert Score (1-5)")
    plt.legend()
    plt.tight_layout()
    plt.show()
