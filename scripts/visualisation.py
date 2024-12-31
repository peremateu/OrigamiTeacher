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

# Group and count raw responses
likert_summary = (
    likert_data.groupby(["Metric", "Group", "Score"])
    .size()
    .reset_index(name="Count")
    .pivot(index=["Metric", "Score"], columns="Group", values="Count")
    .fillna(0)
)

# Plot diverging stacked bar chart for each metric
for metric in columns_of_interest:
    metric_data = likert_summary.xs(metric, level="Metric").reset_index()
    metric_data["No Encouragement"] = -metric_data["No Encouragement"]  # Invert No Encouragement values for diverging effect
    
    plt.figure(figsize=(10, 6))
    plt.barh(metric_data["Score"], metric_data["Encouragement"], color="lightblue", label="Encouragement")
    plt.barh(metric_data["Score"], metric_data["No Encouragement"], color="salmon", label="No Encouragement")
    plt.axvline(0, color="black", linewidth=0.8)  # Add vertical line at 0
    plt.title(f"Diverging Stacked Bar Chart for {metric.replace('_', ' ')}")
    plt.xlabel("Count")
    plt.ylabel("Likert Score (1-5)")
    plt.legend(loc="upper right")
    plt.tight_layout()
    plt.show()