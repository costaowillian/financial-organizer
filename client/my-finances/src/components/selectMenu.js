import React, { useState } from 'react';

const SelectMenu = ({ options, onChange }) => {
  const [selectedOption, setSelectedOption] = useState('');

  const handleSelectChange = (event) => {
    const selectedValue = event.target.value;
    setSelectedOption(selectedValue);
    onChange(selectedValue);
  };

  return (
    <select className="form-control" value={selectedOption} onChange={handleSelectChange}>
      <option value="">Selecione...</option>
      {options.map((option, index) => (
        <option key={index} value={option.value}>
          {option.label}
        </option>
      ))}
    </select>
  );
};

export default SelectMenu;