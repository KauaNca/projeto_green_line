package com.example.mobile;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

public class Mascaras {
    public void nome(EditText editText){
    editText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            String filtrado = s.toString().replaceAll("[^a-zA-Zà-úÀ-Ú\\s]", "").replaceAll("\\s+", " ");
            if (!s.toString().equals(filtrado)) {
                s.replace(0, s.length(), filtrado); // Atualiza o texto sem necessidade de setText()
            }
        }
    });}
    public void cpf(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            private final String mask = "###.###.###-##";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating || s.length() > 14) return;
                isUpdating = true;

                String unmasked = s.toString().replaceAll("\\D", ""); // Remove tudo que não for número
                StringBuilder masked = new StringBuilder();

                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && unmasked.length() > i) {
                        masked.append(m);
                    } else if (i < unmasked.length()) {
                        masked.append(unmasked.charAt(i));
                        i++;
                    } else {
                        break;
                    }
                }

                s.replace(0, s.length(), masked.toString());
                isUpdating = false;
            }
        });

    }
    public void telefone(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            private final String mask8 = "(##) ####-####";  // Telefone fixo
            private final String mask9 = "(##) #####-####"; // Celular

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating || s.length() > 15) return;
                isUpdating = true;

                String unmasked = s.toString().replaceAll("\\D", ""); // Remove tudo que não for número
                String mask = unmasked.length() > 10 ? mask9 : mask8; // Escolhe a máscara certa

                StringBuilder masked = new StringBuilder();
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && unmasked.length() > i) {
                        masked.append(m);
                    } else if (i < unmasked.length()) {
                        masked.append(unmasked.charAt(i));
                        i++;
                    } else {
                        break;
                    }
                }

                s.replace(0, s.length(), masked.toString());
                isUpdating = false;
            }
        });

    }
    public void email(EditText editText){
        editText.setFilters(new InputFilter[]{
                (source, start, end, dest, dstart, dend) -> {
                    String blockChars = " !#$%^&*(),:;/<>[]{}|\\";
                    for (int i = start; i < end; i++) {
                        if (blockChars.contains(String.valueOf(source.charAt(i)))) {
                            return "";
                        }
                    }
                    return null;
                }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString().replaceAll("\\s+", ""); // Remove espaços
                if (!s.toString().equals(email)) {
                    s.replace(0, s.length(), email);
                }
            }
        });

    }

}
