import pandas as pd
from scipy.stats import ttest_ind, shapiro

# Load the cleaned dataset
file_path = "data/processed/likability_dataset.csv"
data = pd.read_csv(file_path)

# Calculate the mean of the five metrics for each participant
data["Overall_Likability"] = data[
    ["Like_Dislike", "Unfriendly_Friendly", "Unkind_Kind", "Unpleasant_Pleasant", "Awful_Nice"]
].mean(axis=1)

# Separate data by groups based on the new Overall_Likability metric
group_ne = data[data["Group"] == "No Encouragement"]["Overall_Likability"]
group_e = data[data["Group"] == "Encouragement"]["Overall_Likability"]

# Perform Shapiro-Wilk Test for normality on both groups
shapiro_ne_stat, shapiro_ne_p = shapiro(group_ne)
shapiro_e_stat, shapiro_e_p = shapiro(group_e)

# Display results of the Shapiro-Wilk Test
print("Shapiro-Wilk Test Results for Normality:")
print(f"No Encouragement Group: W-statistic = {shapiro_ne_stat}, p-value = {shapiro_ne_p}")
print(f"Encouragement Group: W-statistic = {shapiro_e_stat}, p-value = {shapiro_e_p}")

# Perform Independent Samples t-test
t_stat, t_p_value = ttest_ind(group_ne, group_e, alternative="greater")

# Display results of the t-test
print("Independent Samples T-Test Results:")
print(f"T-statistic = {t_stat}, p-value = {t_p_value}")


