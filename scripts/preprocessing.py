import pandas as pd
import os

# Load datasets
file_ne = "data/raw/Group NE Survey.csv"  # Replace with actual path
file_e = "data/raw/Group E Survey.csv"    # Replace with actual path

data_ne = pd.read_csv(file_ne, header=None)
data_e = pd.read_csv(file_e, header=None)

# Assign proper column names based on dataset structure
column_names = [
    "Timestamp", "Gender", "Age", "Interacted_Social_Robot", "Origami_Skills",
    "Q1", "Q2", "Q3", "Q4", "Q5", "Q6", "Q7", "Q8", "Q9", "Q10", 
    "Like_Dislike", "Unfriendly_Friendly", "Unkind_Kind", 
    "Unpleasant_Pleasant", "Awful_Nice", "Extra1", "Extra2", 
    "Extra3", "Extra4", "Extra5", "Extra6", "Extra7", "Extra8", "Extra9"
]

data_ne.columns = column_names
data_e.columns = column_names

# Select columns corresponding to Q to U (Likability metrics) and add group labels
columns_of_interest = ["Like_Dislike", "Unfriendly_Friendly", "Unkind_Kind", 
                        "Unpleasant_Pleasant", "Awful_Nice"]

data_ne_subset = data_ne[columns_of_interest].copy()
data_ne_subset['Group'] = 'No Encouragement'

data_e_subset = data_e[columns_of_interest].copy()
data_e_subset['Group'] = 'Encouragement'

# Combine datasets
combined_data = pd.concat([data_ne_subset, data_e_subset], ignore_index=True)

# Remove rows where all likability ratings are NaN or missing
combined_data.dropna(subset=columns_of_interest, how='all', inplace=True)

# Remove rows with empty string entries
combined_data = combined_data[
    ~combined_data[columns_of_interest].apply(lambda row: row.str.strip().eq("").all(), axis=1)
]

# Ensure the directory structure exists
processed_dir = "data/processed"
os.makedirs(processed_dir, exist_ok=True)

# Save the preprocessed dataset
output_path = os.path.join(processed_dir, "likability_dataset.csv")
combined_data.to_csv(output_path, index=False)

print(f"Preprocessed dataset saved at: {output_path}")
