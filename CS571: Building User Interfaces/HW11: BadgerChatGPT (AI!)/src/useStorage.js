import { useState, useEffect } from 'react';

export default function useStorage(storageKey, initialValue) {
    // try to get messages and persona from key otherwise use initial value
    const [data, setData] = useState(() => {
        try {
            const saved = localStorage.getItem(storageKey);
            return saved ? JSON.parse(saved) : initialValue;
        } catch {
            return initialValue;
        }
    });

    // set the key with the new persona or messages
    useEffect(() => {
        localStorage.setItem(storageKey, JSON.stringify(data));
    }, [storageKey, data]);

    return [data, setData];
}
