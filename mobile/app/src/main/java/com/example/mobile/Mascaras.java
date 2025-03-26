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
    public void cpf(EditText editText) {
        // Configurar limite de caracteres no EditText
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(14)});

        editText.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            private final String mask = "###.###.###-##";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) return; // Evita loops
                isUpdating = true;

                // Remove tudo que não for número
                String unmasked = s.toString().replaceAll("\\D", "");
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
    public void telefone(EditText editText) {
        // Configurar limite de caracteres no EditText
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});

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
                if (isUpdating) return; // Evita loops desnecessários
                isUpdating = true;

                // Remove tudo que não for número
                String unmasked = s.toString().replaceAll("\\D", "");
                // Escolhe a máscara apropriada com base no tamanho do texto
                String mask = unmasked.length() > 10 ? mask9 : mask8;

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
