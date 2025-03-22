import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import os

# Load the dataset
file_path = "data/processed/likability_dataset.csv"
data = pd.read_csv(file_path)

# Define the measures of interest
columns_of_interest = ["Like_Dislike", "Unfriendly_Friendly", "Unkind_Kind", 
                        "Unpleasant_Pleasant", "Awful_Nice"]

# Calculate mean and standard deviation for each measure and group
summary = (
    data.melt(id_vars="Group", value_vars=columns_of_interest, 
              var_name="Measure", value_name="Score")
    .groupby(["Measure", "Group"])["Score"]
    .agg(["mean", "std"])
    .reset_index()
)

# Prepare data for plotting
measures = summary["Measure"].unique()
x = np.arange(len(measures))  # Positions for the measures
width = 0.35  # Width of the bars

# Separate the data for the two groups
encouragement_means = summary[summary["Group"] == "Encouragement"]["mean"]
encouragement_stds = summary[summary["Group"] == "Encouragement"]["std"]
no_encouragement_means = summary[summary["Group"] == "No Encouragement"]["mean"]
no_encouragement_stds = summary[summary["Group"] == "No Encouragement"]["std"]


# Ensure the folder exists
output_dir = "data/visualisations"
os.makedirs(output_dir, exist_ok=True)

# Calculate the overall likability score for each participant
data["Overall_Likability"] = data[columns_of_interest].mean(axis=1)

# Calculate mean and standard deviation for the overall likability by group
overall_summary = data.groupby("Group")["Overall_Likability"].agg(["mean", "std"]).reset_index()

# Plot for each measure (dark gray: No Encouragement, striped: Encouragement)
plt.figure(figsize=(10, 6))
plt.bar(x - width/2, no_encouragement_means, width, yerr=no_encouragement_stds, 
        capsize=5, label="No Encouragement", color="grey", edgecolor="black")
plt.bar(x + width/2, encouragement_means, width, yerr=encouragement_stds, 
        capsize=5, label="Encouragement", color="white", edgecolor="black", hatch="//")

# Add labels and formatting
plt.xticks(x, [measure.replace("_", "-") for measure in measures], rotation=45, ha="right")
plt.title("Mean Scores by Measure and Group", fontsize=14)
plt.ylabel("Mean Score (1-5)", fontsize=12)
plt.xlabel("Measure", fontsize=12)
plt.ylim(0, 5)
plt.legend(loc="upper left", bbox_to_anchor=(1, 1), fontsize=10, frameon=False)
plt.grid(axis="y", linestyle="--", alpha=0.7)

# Save the corrected figure for each measure
output_path_measures = os.path.join(output_dir, "likability_measures.png")
plt.tight_layout()
plt.savefig(output_path_measures, dpi=300)  # Save with high resolution
plt.show()

# Reorder the overall summary to ensure "No Encouragement" is always on the left
overall_summary = overall_summary.sort_values(by="Group", key=lambda x: x.map({"No Encouragement": 0, "Encouragement": 1}))

# Plot for the overall likability (dark gray: No Encouragement, striped: Encouragement)
plt.figure(figsize=(8, 6))
plt.bar(overall_summary["Group"], overall_summary["mean"], yerr=overall_summary["std"], 
        capsize=5, color=["grey", "white"], edgecolor="black", hatch=["", "//"])
plt.title("Mean Overall Likability Scores by Group", fontsize=14)
plt.ylabel("Mean Likability Score (1-5)", fontsize=12)
plt.xlabel("Group", fontsize=12)
plt.ylim(0, 5)
plt.grid(axis="y", linestyle="--", alpha=0.7)

# Save the corrected figure for overall likability
overall_output_path = os.path.join(output_dir, "overall_likability.png")
plt.tight_layout()
plt.savefig(overall_output_path, dpi=300)  # Save with high resolution
plt.show()

output_path_measures, overall_output_path

