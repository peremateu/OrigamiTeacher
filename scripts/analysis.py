import pandas as pd
from scipy.stats import mannwhitneyu

# Load the cleaned dataset
file_path = "data/processed/likability_dataset.csv"
data = pd.read_csv(file_path)

# Separate data by groups
group_ne = data[data["Group"] == "No Encouragement"]
group_e = data[data["Group"] == "Encouragement"]

# Perform Mann-Whitney U test for each column related to likability
columns_of_interest = ["Like_Dislike", "Unfriendly_Friendly", "Unkind_Kind", 
                        "Unpleasant_Pleasant", "Awful_Nice"]

results = {}
for column in columns_of_interest:
    stat, p_value = mannwhitneyu(group_ne[column], group_e[column], alternative="two-sided")
    results[column] = {"U-statistic": stat, "p-value": p_value}

# Convert results to a DataFrame for better readability
results_df = pd.DataFrame.from_dict(results, orient="index")
print(results_df)


