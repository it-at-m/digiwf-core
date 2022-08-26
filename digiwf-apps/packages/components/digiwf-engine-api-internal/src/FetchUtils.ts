import {Configuration} from '@/api';

export class FetchUtils {

    /**
     * Liefert eine default GET-Config für fetch
     */
    static getGETConfig(): RequestInit {
        return {
            headers: this.getHeaders(),
            mode: 'cors',
            credentials: 'same-origin',
            redirect: 'manual'
        };
    }

    /**
     * Liefert eine default POST-Config für fetch
     * @param body Optional zu übertragender Body
     */
    // eslint-disable-next-line
    static getDELETEConfig(): RequestInit {
        const headers = FetchUtils.getHeaders();
        return {
            method: 'DELETE',
            headers,
            mode: 'cors',
            credentials: 'same-origin',
            redirect: "manual"
        };
    }

    static getAxiosConfig(fetchConfig: RequestInit): Configuration {
        const cfg = new Configuration();
        cfg.baseOptions = fetchConfig;
        return cfg;
    }

    /**
     * Liefert eine default PUT-Config für fetch
     * In dieser wird, wenn vorhanden, die Version der zu aktualisierenden Entität
     * als "If-Match"-Header mitgesetzt.
     * @param body Optional zu übertragender Body
     */
    // eslint-disable-next-line
    static getPUTConfig(body: any): RequestInit {
        const headers = FetchUtils.getHeaders();
        if (body.version) {
            headers.append("If-Match", body.version);
        }
        return {
            method: 'PUT',
            body: body ? JSON.stringify(body) : undefined,
            headers,
            mode: 'cors',
            credentials: 'same-origin',
            redirect: "manual"
        };

    }

    /**
     * Liefert eine default PATCH-Config für fetch
     * In dieser wird, wenn vorhanden, die Version der zu aktualisierenden Entität
     * als "If-Match"-Header mitgesetzt.
     * @param body Optional zu übertragender Body
     */
    // eslint-disable-next-line
    static getPATCHConfig(body: any): RequestInit {
        const headers = FetchUtils.getHeaders();
        if (body.version !== undefined) {
            headers.append("If-Match", body.version);
        }
        return {
            method: 'PATCH',
            body: body ? JSON.stringify(body) : undefined,
            headers,
            mode: 'cors',
            credentials: 'same-origin',
            redirect: "manual"
        };
    }

    /**
     *  Baut den Header fuer den Request auf
     * @returns {Headers}
     */
    static getHeaders(): Headers {
        const headers = new Headers({
            'Content-Type': 'application/json'
        });
        const csrfCookie = this._getXSRFToken();
        if (csrfCookie !== '') {
            headers.append('X-XSRF-TOKEN', csrfCookie);
        }
        return headers;
    }

    /**
     * Liefert den XSRF-TOKEN zurück.
     * @returns {string|string}
     */
    static _getXSRFToken(): string {
        const help = document.cookie.match('(^|;)\\s*' + 'XSRF-TOKEN' + '\\s*=\\s*([^;]+)');
        return (help ? help.pop() : '') as string;
    }

}
